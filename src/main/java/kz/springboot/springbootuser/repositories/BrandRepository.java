package kz.springboot.springbootuser.repositories;

import kz.springboot.springbootuser.entities.Brand;
import kz.springboot.springbootuser.entities.Country;
import kz.springboot.springbootuser.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long>{


}
