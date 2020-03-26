import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeId")
    public int typeId;
    @OneToMany(mappedBy = "tipulDeIssue")
    List<Issue> issueList;

    @Column(name="typeName")
    public String typeName;

    public Type(int typeId, String typeName) {
        this.typeId=typeId;
        this.typeName = typeName;
    }

    public Type(String typeName) {
        this.typeName = typeName;
    }

    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
