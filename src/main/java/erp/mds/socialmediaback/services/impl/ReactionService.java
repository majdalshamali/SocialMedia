package erp.mds.socialmediaback.services.impl;

import erp.mds.socialmediaback.dto.request.ReactionDtoRequest;
import erp.mds.socialmediaback.dto.response.ReactionDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Post;
import erp.mds.socialmediaback.entity.business.Reaction;
import erp.mds.socialmediaback.mapper.Mapper;
import erp.mds.socialmediaback.repository.ReactionRepository;
import erp.mds.socialmediaback.services.IReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionService implements IReactionService {

    private final ReactionRepository reactionRepository;

    @Override
    public Reaction save(ReactionDtoRequest reactionDto, Users user) {

        Reaction reaction = Reaction.builder()
                .post(new Post(reactionDto.getPostId()))
                .reactionType(reactionDto.getReactionType())
                .user(user)
                .build();
        return reactionRepository.save(reaction);
    }

    @Override
    public List<ReactionDtoResponse> listOfReactionOfPost(Integer postId) {
        List<Reaction> reactions = reactionRepository.listOfReactionOfPost(postId);
        List<ReactionDtoResponse> listOfReaction = new ArrayList<>();
        reactions.forEach(reaction -> {
            listOfReaction.add(Mapper.toReactionDtoResponse(reaction));
        });
        return listOfReaction;
    }

}
