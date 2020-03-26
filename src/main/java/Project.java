import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="projectId")
    public int projectId;

    @OneToMany(mappedBy = "projectulDeCareCoresponde")
    public List<Issue> issueList;

    @Column(name="name")
    public String name;

    @Column(name="identifier")
    public String identifier;

    @Column(name="description")
    public String description;

    public Project(int projectId, String name, String identifier, String description) {
        this.projectId=projectId;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
    }

    public Project(String name, String identifier, String description) {
        this.name = name;
        this.identifier = identifier;
        this.description = description;
    }

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
