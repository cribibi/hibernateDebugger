import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    public int userId;
    @OneToMany(mappedBy = "userulCareAScrisCommentul")
    public List<Comments> commenturi;

    @OneToMany(mappedBy = "userulCareAScrisProblema")
    public List<Issue> issueList;

    @Column(name="userName")
    public String userName;


    public User(int userId, String userName) {
        this.userId=userId;
        this.userName = userName;
    }

    public User(String userName) {
        this.userName = userName;
    }
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
