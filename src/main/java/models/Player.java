package models;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
public class Player implements Serializable{
    private String nickname;

    public Player(String _nickname) {
        this.nickname = _nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}
