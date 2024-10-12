package erp.mds.socialmediaback.services;

import erp.mds.socialmediaback.dto.request.ReactionDtoRequest;
import erp.mds.socialmediaback.dto.response.ReactionDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Reaction;

import java.util.List;

public interface IReactionService {

    Reaction save(ReactionDtoRequest reactionDto, Users user);

    List<ReactionDtoResponse> listOfReactionOfPost(Integer postId);
}
