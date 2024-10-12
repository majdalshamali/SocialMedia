package erp.mds.socialmediaback.services.impl;

import erp.mds.socialmediaback.constant.SocialConstant;
import erp.mds.socialmediaback.dto.request.PostDtoRequest;
import erp.mds.socialmediaback.dto.response.CommentDtoResponse;
import erp.mds.socialmediaback.dto.response.PostDtoResponse;
import erp.mds.socialmediaback.dto.response.ReactionDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Comment;
import erp.mds.socialmediaback.entity.business.Post;
import erp.mds.socialmediaback.mapper.Mapper;
import erp.mds.socialmediaback.repository.CommentRepository;
import erp.mds.socialmediaback.repository.PostRepository;
import erp.mds.socialmediaback.repository.ReactionRepository;
import erp.mds.socialmediaback.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    //this function is to create new post
    @Override
    public Post save(PostDtoRequest postDto, Users user) {

        //PostDto hold the information of the user Input
        Post post = Post.builder()
                .text(postDto.getText())
                .user(user)
                .countOfComments(0)
                .countOfLikes(0)
                .build();
        return postRepository.save(post);
    }

    //get the post information
    @Override
    public Optional<Post> get(Integer id) {
        return postRepository.findById(id);
    }

    //full post means get the comments and reaction in the same request
    @Override
    public Optional<PostDtoResponse> getFullPost(Integer id) {

        PostDtoResponse postDtoResponse;//define the object to hold the information of the post
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            postDtoResponse = Mapper.toPostDtoResponse(post.get());
            List<CommentDtoResponse> commentsDto = new ArrayList<CommentDtoResponse>();
            List<ReactionDtoResponse> reactionsDto = new ArrayList<>();
            post.get().getComments().forEach(comment -> {
                commentsDto.add(Mapper.toCommentDtoResponse(comment));
            });
            post.get().getReactions().forEach(reaction -> {
                reactionsDto.add(Mapper.toReactionDtoResponse(reaction));
            });
            postDtoResponse.setComments(commentsDto);
            postDtoResponse.setReactions(reactionsDto);
            return Optional.of(postDtoResponse);
        }
        return Optional.empty();
    }

    //get the post information based on ID without comments and reactions
    @Override
    public Optional<PostDtoResponse> getSmallPost(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(Mapper::toPostDtoResponse);
    }

    //get all the post contain the keyword
    @Override
    public List<PostDtoResponse> listOfAllPost(String keyword) {
        List<Post> posts = postRepository.listOfPost(keyword);
        List<PostDtoResponse> postDtoResponses = new ArrayList<>();
        posts.forEach(post -> {
            postDtoResponses.add(getSmallPost(post.getId()).get());
        });
        return postDtoResponses;
    }

    //get all the post contain keyword according to page number
    @Override
    public List<PostDtoResponse> search(String keyword, Integer pageNum) {
        Sort sort = Sort.by("createdAt").ascending();
        List<PostDtoResponse> postDtoResponses = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNum - 1, SocialConstant.Post_PAGE_SIZE,sort);
        List<Post> posts = postRepository.search(keyword,pageable).getContent();
        posts.forEach(post -> {
            postDtoResponses.add(getSmallPost(post.getId()).get());
        });
        return postDtoResponses;
    }
}
