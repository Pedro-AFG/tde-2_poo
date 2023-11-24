package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_aluno")
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_aluno;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String nomeAluno;

    @ManyToMany(mappedBy = "listaAlunos")
    public Set<Curso> listaCursos = new HashSet<>();

    public Aluno(String matricula, String nomeAluno) {
        this.matricula = matricula;
        this.nomeAluno = nomeAluno;
    }

    public Aluno() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Set<Curso> getListaCursos() {
        return listaCursos;
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "Id=" + id_aluno +
            ", Matricula='" + matricula + '\'' +
            ", Nome='" + nomeAluno + '\'' +
            '}';
    }
}
