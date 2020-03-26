import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commentId")
    public int commentId;

    @Column(name="commentText")
    public String commentText;

    @Column(name = "dateTimeCreated")
    public LocalDateTime dateTimeCreated;

    @ManyToOne
    @JoinColumn(name = "submitterId", referencedColumnName = "userId")
    public User userulCareAScrisCommentul;

    public Comments(int commentId, String commentText, User userulCareAScrisCommentul) {
        this.commentId= commentId;
        this.commentText = commentText;
        this.dateTimeCreated = LocalDateTime.now();
        this.userulCareAScrisCommentul= userulCareAScrisCommentul;
    }

    public Comments(String commentText, User userulCareAScrisCommentul) {
        this.commentText = commentText;
        this.dateTimeCreated = LocalDateTime.now();
        this.userulCareAScrisCommentul= userulCareAScrisCommentul;
    }

    public Comments() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", dateTimeCreated=" + dateTimeCreated +
                ", userulCareAScrisCommentul=" + userulCareAScrisCommentul.userName +
                '}';
    }
}
