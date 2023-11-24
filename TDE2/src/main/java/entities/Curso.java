package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nomeCurso;

    @Column(nullable = false)
    private int cargaHoraria;

    @ManyToMany
    @JoinTable(name = "tb_aluno_curso",joinColumns = @JoinColumn(name = "aluno_id"),inverseJoinColumns = @JoinColumn(name = "curso_id"))
    public Set<Aluno> listaAlunos = new HashSet<>();

    public Curso(String codigo, String nomeCurso, int cargaHoraria) {
        this.codigo = codigo;
        this.nomeCurso = nomeCurso;
        this.cargaHoraria = cargaHoraria;
    }

    public Curso() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Set<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    @Override
    public String toString() {
        return "Curso{" +
            "Codigo=" + codigo +
            ", Nome='" + nomeCurso + '\'' +
            ", Carga Horaria=" + cargaHoraria +
            '}';
    }
}
