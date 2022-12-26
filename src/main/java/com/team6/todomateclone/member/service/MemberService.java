package com.team6.todomateclone.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.common.jwt.JwtUtil;
import com.team6.todomateclone.member.dto.*;
import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.member.mapper.MemberMapper;
import com.team6.todomateclone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.EMAIL_NOT_FOUND_MSG;
import static com.team6.todomateclone.common.exception.CustomErrorCodeEnum.PASSWORD_NOT_MATCH_MSG;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    /* 기본 이미지 url */
    private static final String defaultImage = "https://cdn.icon-icons.com/icons2/1875/PNG/512/user_120285.png";

    public void signup(RequestSignupMemberDto request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        // 이메일 중복 검사
        memberRepository.findByEmail(email).ifPresent(
                m -> {
                    throw new CustomErrorException(EMAIL_NOT_FOUND_MSG);
                }
        );

        Member member = memberMapper.toEntity(email, password, defaultImage);
        memberRepository.save(member);
    }

    public void login(RequestLoginMemberDto request, HttpServletResponse response) {
        Member member = checkEmail(request);

        if (!(passwordEncoder.matches(request.getPassword(), member.getPassword()))) {
            throw new CustomErrorException(PASSWORD_NOT_MATCH_MSG);
        }

        String accessToken = jwtUtil.createToken(member.getEmail());

        response.addHeader(JwtUtil.AUTHORIZATION_ACCESS, accessToken);
    }

    public ResponseInfoMemberDto getMemberInfo(Long memberId) {
        Member member = checkMember(memberId);
        return new ResponseInfoMemberDto(member);
    }

    public ResponseUpdateInfoMemberDto updateInfo(Long memberId, RequestUpdateInfoMemberDto request){
        Member member = checkMember(memberId);
        member.updateInfo(request.getNickname(), request.getDescription());
        return new ResponseUpdateInfoMemberDto(member);
    }

    public ResponseUpdateImageMemberDto updateImage(Long memberId, MultipartFile multipartFile) throws IOException {
        String profileImageUrl = changeImageToUrl(multipartFile);
        Member member = checkMember(memberId);
        member.updateImage(profileImageUrl);
        return new ResponseUpdateImageMemberDto(member);
    }

    public ResponseUpdateImageMemberDto updateToDefaultImage(Long memberId){
        Member member = checkMember(memberId);
        member.updateImage(defaultImage);
        return new ResponseUpdateImageMemberDto(member);
    }

    /* AWS S3 관련: Image -> Url */
    /* 기본 이미지로 변경 */
    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;
    private final AmazonS3Client amazonS3Client;

    private String changeImageToUrl(MultipartFile multipartFile) throws IOException {
            /* 파일 이름과 크기 */
            String originalName = multipartFile.getOriginalFilename();
            long size = multipartFile.getSize();

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(multipartFile.getContentType());
            objectMetaData.setContentLength(size);
            /* S3 업로드 */
            amazonS3Client.putObject(new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
             );
            /* URL */
            String imageUrl = amazonS3Client.getUrl(S3Bucket, originalName).toString();
            return imageUrl;
    }
    /* 유저 확인 */

    private Member checkMember(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(()->new CustomErrorException(CustomErrorCodeEnum.MEMBER_NOT_FOUND_MSG));
    }

    // 유저 Email 존재 여부 확인 메서드
    private Member checkEmail(RequestLoginMemberDto request) {
        return memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomErrorException(EMAIL_NOT_FOUND_MSG));
    }
}


