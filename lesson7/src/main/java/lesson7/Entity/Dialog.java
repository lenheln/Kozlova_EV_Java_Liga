//package lesson7.Entity;
//
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Data
//@NoArgsConstructor
//@Table(name = "dialog")
//public class Dialog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToMany()
//    @JoinTable(
//            name = "userdialog",
//            joinColumns = { @JoinColumn(name = "dialog_id", referencedColumnName = "id") },
//            inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }
//    )
//    private Set<User> users = new HashSet<>(0);
//
////    @OneToMany(mappedBy = "dialog")
////    List<Message> messages = new ArrayList<>();
//
//
//    public Dialog(List<User> users) {
//
//    }
//}
