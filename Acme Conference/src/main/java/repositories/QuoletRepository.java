
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.admin.id = ?1")
	public Collection<Quolet> getQuoletsByAdmin(final Integer adminId);

	//DASHBOARD
	//Opcion 1: Numero de quolets por conferencia
	@Query("select avg(1.0*(select count(q.conference) from Quolet q where q.conference.id = c.id)), sqrt(1.0*sum(1.0*(select count (q.conference) from Quolet q where q.conference.id = c.id) * (select count(q.conference) from Quolet q where q.conference.id = c.id)) / count(c) - avg(1.0*(select count(q.conference) from Quolet q where q.conference.id = c.id)) * avg(1.0*(select count(q.conference) from Quolet q where q.conference.id = c.id))) from Conference c where c.finalMode=1")
	public List<Object[]> getAvgDesvNumberQuoletsByConference();

	//Opcion 2: Numero de quolets por administrador
	@Query("select avg(1.0*(select count(q.admin) from Quolet q where q.admin.id = a.id)),sqrt(1.0*sum(1.0*(select count (q.admin) from Quolet q where q.admin.id = a.id) *(select count(q.admin) from Quolet q where q.admin.id = a.id)) / count(a) - avg(1.0*(select count(q.admin) from Quolet q where q.admin.id = a.id)) *avg(1.0*(select count(q.admin) from Quolet q where q.admin.id = a.id))) from Administrator a")
	public List<Object[]> getAvgDesvNumberQuoletsByAdministrator();

}
