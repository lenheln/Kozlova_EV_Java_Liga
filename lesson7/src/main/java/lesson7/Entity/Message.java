package lesson7.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    String msg;

    @ManyToOne
    @JoinColumn(name = "authorId")
    User authorId;

    @ManyToOne
    @JoinColumn(name = "recieverId")
    User recieverId;

    @Column(name = "dateTime")
    Date date = new Date();

    public Message(String msg, User authorId, User recieverId) {
        this.msg = msg;
        this.authorId = authorId;
        this.recieverId = recieverId;
    }
}
