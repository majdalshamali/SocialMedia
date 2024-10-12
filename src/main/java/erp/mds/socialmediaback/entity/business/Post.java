package erp.mds.socialmediaback.entity.business;

import erp.mds.socialmediaback.entity.BaseEntity;
import erp.mds.socialmediaback.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 255,name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reaction> reactions = new ArrayList<>();

    @Transient
    private Integer countOfLikes;

    @Transient
    private Integer countOfComments;

    public Post(Integer id){
        this.id = id;
    }

}
