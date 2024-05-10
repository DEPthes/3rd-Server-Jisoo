package hellojpa;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args){
        // 엔티티 매니저 팩토리는 웹 서버가 올라오는 시점에 DB당 하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 엔티티 매니저는 고객 요청이 올 때마다 새로 만들어진다.
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작
        try {
            Member member = new Member(200L, "member200");
            em.persist(member); // 등록

            // 조회
            /*Member findMember = em.find(Member.class, 200L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());*/


            // 수정
            /*Member findMember = em.find(Member.class, 200L);
            findMember.setName("helloA"); //-> 이대로 끝. persist 안해도 된다. JPA가 알아서 변경 감지 후 커밋 시점에 업데이트 쿼리를 날린다.*/


            //삭제
            //em.remove(findmember); // remove 메소드만으로 삭제 가능


            tx.commit(); // 커밋 시점에 flush 자동으로 날아간다 -> 저장
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
