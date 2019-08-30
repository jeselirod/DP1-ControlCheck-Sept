
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.author.id=?1")
	public Collection<Submission> getSubmissionByAuthor(Integer authorId);

	//under-reviwed
	@Query("select s from Submission s where s.conference.admin.id=?1 and s.conference.finalMode=1 and  s.status=0")
	public Collection<Submission> getSubmissionByAdministratorStatus0(Integer adminId);

	//Rejected
	@Query("select s from Submission s where s.conference.admin.id=?1 and s.conference.finalMode=1 and s.status=1")
	public Collection<Submission> getSubmissionByAdministratorStatus1(Integer adminId);

	//Accepted
	@Query("select s from Submission s where s.conference.admin.id=?1 and s.conference.finalMode=1 and s.status=2")
	public Collection<Submission> getSubmissionByAdministratorStatus2(Integer adminId);

	@Query("select s from Submission s where ?1 member of s.reviwers")
	public Collection<Submission> getSubmissionByReviwers(Integer reviwerId);

}
