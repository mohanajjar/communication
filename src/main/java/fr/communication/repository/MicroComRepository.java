package fr.communication.repository;

import fr.communication.domain.MicroCom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroComRepository  extends JpaRepository<MicroCom,Long>{
}
