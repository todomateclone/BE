package com.team6.todomateclone.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team6.todomateclone.common.exception.CustomErrorCodeEnum;
import com.team6.todomateclone.common.exception.CustomErrorException;
import com.team6.todomateclone.member.dto.*;
import com.team6.todomateclone.member.entity.Member;
import com.team6.todomateclone.member.mapper.MemberMapper;
import com.team6.todomateclone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    /* 기본 이미지 url */
    private static final String defaultImage = "https://cdn.icon-icons.com/icons2/1875/PNG/512/user_120285.png";

    public void signup(RequestSignupMemberDto request) {
        Member member = memberMapper.toEntity(request, defaultImage);
        memberRepository.save(member);
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

    /* 기본 이미지로 변경 */
    public ResponseUpdateImageMemberDto updateToDefaultImage(Long memberId){
        Member member = checkMember(memberId);
        member.updateImage(defaultImage);
        return new ResponseUpdateImageMemberDto(member);
    }

    /* AWS S3 관련: Image -> Url */
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

}


