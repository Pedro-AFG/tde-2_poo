package controller;

import entities.Aluno;
import entities.Curso;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TDE2");

        EntityManager em = emf.createEntityManager();

        try {
            int option = 1;

            while (option != 0) {
                System.out.println("Digite 1 para adicionar um aluno: ");
                System.out.println("Digite 2 para adicionar um curso: ");
                System.out.println("Digite 3 para mostrar todos os cursos: ");
                System.out.println("Digite 4 para mostrar todos os alunos: ");
                System.out.println("Digite 5 para adicionar um aluno a determinado curso: ");
                System.out.println("Digite 6 para mostrar todos os alunos e seus cursos: ");
                System.out.println("Digite 7 para mostrar todos os alunos de um curso: ");
                System.out.println("Digite 0 para sair: ");

                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                switch (option) {

                    case 1:
                        System.out.println("Digite o número da matrícula do aluno:");
                        String matricula = scan.nextLine();
                        System.out.println("Digite o nome do aluno:");
                        String nome = scan.nextLine();
                        Aluno aluno = new Aluno(matricula, nome);
                        inserirAluno(emf, aluno);
                        break;

                    case 2:
                        System.out.println("Digite o código do curso:");
                        String codigoCurso = scan.nextLine();
                        System.out.println("Digite o nome do curso:");
                        String nomeCurso = scan.nextLine();
                        System.out.println("Digite a carga horária do curso:");
                        int cargaHoraria = scan.nextInt();
                        Curso curso = new Curso(codigoCurso, nomeCurso, cargaHoraria);
                        inserirCurso(emf, curso);
                        break;

                    case 3:
                        try {
                            mostraCursos(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        try {
                            mostraAlunos(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 5:
                        try {
                            String nomeDoCurso;
                            String matriculaAluno;

                            Scanner scan2 = new Scanner(System.in);
                            System.out.println("Escreva a matrícula do aluno que você deseja alocar");
                            mostraAlunos(emf);
                            matriculaAluno = scan2.nextLine();
                            Aluno a1 = buscaAlunoPelaMatricula(emf, matriculaAluno);
                            System.out.println("Selecione o nome do curso");
                            mostraCursos(emf);
                            nomeDoCurso = scan2.nextLine();
                            Curso c1 = buscaCursoPeloNome(emf, nomeDoCurso);

                            em.getTransaction().begin();

                            c1.getListaAlunos().add(a1);

                            em.persist(c1);
                            em.getTransaction().commit();

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 6:
                        try {
                            mostraAlunosECurso(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 7:
                        try {
                            Scanner scan4 = new Scanner(System.in);
                            System.out.println("Digite o nome do curso");
                            String cursoEscolhido = scan4.nextLine();
                            mostraAlunosDeCurso(emf, cursoEscolhido);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        break;
                }

            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void inserirAluno(EntityManagerFactory emf, Aluno a1) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(a1);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            em.close();
        }

    }


    public static void inserirCurso(EntityManagerFactory emf, Curso c1) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(c1);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public static void mostraCursos(EntityManagerFactory emf) {


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            Query query = em.createQuery("select c from Curso c");
            List<Curso> resultList = query.getResultList();

            for(Curso x: resultList){
                System.out.println(x);
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public static void mostraAlunosECurso(EntityManagerFactory emf) {


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query query = em.createQuery("SELECT a FROM Aluno a JOIN a.cursos c");
            List<Aluno> resultList = query.getResultList();

            Query query2 = em.createQuery("SELECT c.nome FROM Aluno a JOIN a.cursos c");
            List<String> resultList2 = query2.getResultList();

            int i = 0;
            for(Aluno x: resultList){

                System.out.print(x);
                System.out.println(" {" + resultList2.get(i) + "}");
                i = i+1;

            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public static void mostraAlunos(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query query = em.createQuery("SELECT a FROM Aluno a");
            List<Aluno> resultList = query.getResultList();

            for(Aluno x: resultList){

                System.out.println(x);
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            em.close();
        }

    }


    public static Aluno buscaAlunoPelaMatricula(EntityManagerFactory emf, String matricula) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query query = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = :matricula");
            query.setParameter("matricula", matricula);

            List<Aluno> resultList = query.getResultList();

            Aluno a1 = resultList.get(0);

            return a1;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;

    }


    public static Curso buscaCursoPeloNome(EntityManagerFactory emf, String nomeCurso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Query query = em.createQuery("SELECT c FROM Curso c WHERE c.nome = :nomeCurso");
            query.setParameter("nomeCurso", nomeCurso);

            List<Curso> resultList = query.getResultList();

            Curso c1 = resultList.get(0);

            return c1;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;

    }



    public static void mostraAlunosDeCurso(EntityManagerFactory emf, String nomeCurso) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query query = em.createQuery("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.nome = :nomeCurso");
            query.setParameter("nomeCurso", nomeCurso);
            List<Aluno> resultList = query.getResultList();

            for(Aluno x: resultList){

                System.out.print(x);

            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }
}