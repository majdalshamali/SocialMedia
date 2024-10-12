package erp.mds.socialmediaback.controller;


import erp.mds.socialmediaback.dto.ErrorResponseDto;
import erp.mds.socialmediaback.dto.request.CommentDtoRequest;
import erp.mds.socialmediaback.dto.request.PostDtoRequest;
import erp.mds.socialmediaback.dto.request.ReactionDtoRequest;
import erp.mds.socialmediaback.dto.response.CommentDtoResponse;
import erp.mds.socialmediaback.dto.response.PostDtoResponse;
import erp.mds.socialmediaback.dto.response.ReactionDtoResponse;
import erp.mds.socialmediaback.entity.Users;
import erp.mds.socialmediaback.entity.business.Comment;
import erp.mds.socialmediaback.entity.business.Post;
import erp.mds.socialmediaback.entity.business.Reaction;
import erp.mds.socialmediaback.exception.NotValidInputException;
import erp.mds.socialmediaback.exception.ResourceNotFoundException;
import erp.mds.socialmediaback.mapper.Mapper;
import erp.mds.socialmediaback.services.ICommentService;
import erp.mds.socialmediaback.services.IPostService;
import erp.mds.socialmediaback.services.IReactionService;
import erp.mds.socialmediaback.services.IUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@Validated
@Schema(
        description = "Main Controller"
)
public class MainController {

    private final IUsersService usersService;
    private final IPostService postService;
    private final ICommentService commentService;
    private final IReactionService reactionService;



    @Operation(
            summary = "Add Post  REST API",
            description = "REST API to create new post inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/add")
    public ResponseEntity<PostDtoResponse> addPost(@Valid @RequestBody PostDtoRequest postDto){
        Optional<Users> user = usersService.getUser();
        if(user.isPresent()){

            Post post = postService.save(postDto,user.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Mapper.toPostDtoResponse(post));
        }
        else {
            throw new ResourceNotFoundException("Users","User Name","Authenticated UserName");
        }
    }

    @Operation(
            summary = "Add comment for post REST API",
            description = "REST API to create new comment inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/addComment")
    public ResponseEntity<CommentDtoResponse> addComment(@Valid @RequestBody CommentDtoRequest commentDto)
    {
        Optional<Users> user = usersService.getUser();
        if(user.isPresent()){
            Comment comment = commentService.save(commentDto,user.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Mapper.toCommentDtoResponse(comment));

        }else
        {
            throw new ResourceNotFoundException("Users","User Name","Authenticated UserName");
        }
    }

    @Operation(
            summary = "Add Reaction for a post  REST API",
            description = "REST API to create new reaction inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/addReaction")
    public ResponseEntity<ReactionDtoResponse> addReaction(@Valid @RequestBody ReactionDtoRequest reactionDto){
        Optional<Users> user = usersService.getUser();
        if(user.isPresent()){
            Reaction reaction = reactionService.save(reactionDto,user.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Mapper.toReactionDtoResponse(reaction));
        }
        else
        {
            throw new ResourceNotFoundException("Users","User Name","Authenticated UserName");
        }
    }

    @Operation(
            summary = "get Full or small Post information REST API",
            description = "REST API to get Full (true) or small (false)  Post inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @GetMapping("/post")
    public ResponseEntity<PostDtoResponse> getFullPost(@Valid @Param("postId")  @Min(value = 0, message = "postId should be greater or equal  0")  Integer postId,
                                                       @Valid @Param("full") boolean full){
        Optional<PostDtoResponse> postDtoResponse;
        postDtoResponse = full? postService.getFullPost(postId):postService.getSmallPost(postId);
       if(postDtoResponse.isPresent()){
           return ResponseEntity.status(HttpStatus.OK)
                   .body(postDtoResponse.get());
       }
       else
       {
           throw new ResourceNotFoundException("Post","Post ID",""+postId);
       }
    }

    @Operation(
            summary = "get comments of a post  REST API",
            description = "REST API to get comments of post inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/comment")
    public ResponseEntity<List<CommentDtoResponse>> getCommentOfPost(@Valid @Param("postId") @Min(value = 0, message = "postId should be greater or equal  0")  Integer postId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.listOfCommentsOfPost(postId));
    }

    @Operation(
            summary = "get reactions of a post  REST API",
            description = "REST API to get reactions of post inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/reaction")
    public ResponseEntity<List<ReactionDtoResponse>> getReactionOfPost(@Valid @Param("postID") @Min(value = 0, message = "postId should be greater or equal  0")  Integer postId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(reactionService.listOfReactionOfPost(postId));
    }



    @Operation(
            summary = "get feeds of posts  REST API",
            description = "REST API to get feeds of posts inside Majd Social Media Back"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/posts")
    public ResponseEntity<List<PostDtoResponse>> postsFeed(@Valid @Param("keyword") String keyword,
                                                           @Valid @Param("PageNum") @Min(value = 0, message = "postId should be greater or equal  0") Integer pageNum){
        if(pageNum < 0){
            throw new NotValidInputException("pagNum");
        }
        if(pageNum == 0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postService.listOfAllPost(keyword));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postService.search(keyword,pageNum));
        }
    }

}
