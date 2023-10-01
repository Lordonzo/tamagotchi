package models;

//import jakarta.persistence.*;

//@Entity
//@Table(name = "player")
public class Player {
    //@Id
    //@Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //@Column
    private String nickname;

    public Player(String _nickname) {
        this.nickname = _nickname;
    }
}
