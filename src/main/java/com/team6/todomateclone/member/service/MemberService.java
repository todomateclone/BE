package com.team6.todomateclone.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team6.todomateclone.member.dto.RequestSignupMemberDto;
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

    public void signup(RequestSignupMemberDto request,MultipartFile multipartFile) throws IOException {
        String profileImageUrl = changeImageToUrl(multipartFile);
        Member member = memberMapper.toEntity(request,profileImageUrl);
        memberRepository.save(member);
    }


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

}


