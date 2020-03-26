import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table (name="issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="issueId")
    public int issueId;

    @Column(name="title", nullable = false)
    public String title;

    @Column(name="description")
    public String description;

    @ManyToOne
    @JoinColumn(name="projectId")
    public Project projectulDeCareCoresponde;

    @Column(name = "dateCreated")
    public LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "statusId", columnDefinition = "int default 1")
    public Status statusulIsseului;

    @ManyToOne
    @JoinColumn(name = "submitterId", referencedColumnName = "userId")
    public User userulCareAScrisProblema;

    @ManyToOne
    @JoinColumn(name="typeId")
    public Type tipulDeIssue;

    public Issue(int issueId, String title, String description, Project projectulDeCareCoresponde,
                 Status statusulIsseului, User userulCareAScrisProblema, Type tipulDeIssue) {
        this.issueId=issueId;
        this.title=title;
        this.description=description;
        this.projectulDeCareCoresponde=projectulDeCareCoresponde;
        this.dateCreated=LocalDate.now();
        this.statusulIsseului=statusulIsseului;
        this.userulCareAScrisProblema=userulCareAScrisProblema;
        this.tipulDeIssue=tipulDeIssue;
    }

    public Issue(String title, String description, Project projectulDeCareCoresponde,
                 Status statusulIsseului, User userulCareAScrisProblema,
                 Type tipulDeIssue) {
        this.title=title;
        this.description=description;
        this.projectulDeCareCoresponde=projectulDeCareCoresponde;
        this.dateCreated=LocalDate.now();
        this.statusulIsseului=statusulIsseului;
        this.userulCareAScrisProblema=userulCareAScrisProblema;
        this.tipulDeIssue=tipulDeIssue;
    }

    public Issue() {
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueId=" + issueId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", projectulDeCareCoresponde=" + projectulDeCareCoresponde.name +
                ", dateCreated='" + dateCreated + '\'' +
                ", statusulIsseului=" + statusulIsseului.statusName +
                ", userulCareAScrisProblema=" + userulCareAScrisProblema.userName +
                ", tipulDeIssue=" + tipulDeIssue.typeName +
                '}';
    }
}
