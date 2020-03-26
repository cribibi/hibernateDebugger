import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="statusId")
    public int statusId;
    @OneToMany(mappedBy = "userulCareAScrisProblema")
    List<Issue> issueList;

    @Column(name="statusName")
    public String statusName;

    public Status(int statusId, String statusName) {
        this.statusId=statusId;
        this.statusName = statusName;
    }
    public Status(String statusName) {
        this.statusName = statusName;
    }

    public Status() {
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
