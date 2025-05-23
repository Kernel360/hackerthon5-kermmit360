package hackathon.kermmit360.login;

import hackathon.kermmit360.member.entity.MemberEntity;
import hackathon.kermmit360.member.repository.MemberRepository;
import hackathon.kermmit360.rank.Rank;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubLoginService {

    private final MemberRepository memberRepository;

    public String userLogin(OAuth2AuthenticationToken authentication) {
        Integer id = authentication.getPrincipal().getAttribute("id");
        System.out.println(id);
        MemberEntity user = null;
        if(memberRepository.findByGithubId(id)!=null){
            user = memberRepository.findByGithubId(id);
        }else{
            user = MemberEntity.builder()
                    .githubId(id)
                    .username(authentication.getPrincipal().getAttribute("name"))
                    .email(authentication.getPrincipal().getAttribute("email"))
                    .role("ROLE_USER")
                    .build();

            memberRepository.save(user);
        }
        return user.getUsername();
    }
}
