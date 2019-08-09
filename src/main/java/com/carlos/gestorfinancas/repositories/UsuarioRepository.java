package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Usuario;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	List<Usuario> findByAtivo(boolean ativo);
	
	List<Usuario> findByEmailAndAtivo(String email, boolean ativo);
	
	List<Usuario> findByLoginAndAtivo(String login, boolean ativo);
	
	@Query("SELECT u FROM Usuario u WHERE (u.login = ?1 OR u.email = ?1) AND (u.ativo = ?2)")
	Optional<Usuario> findByLoginOrEmailAndAtivo(String loginOrEmail, boolean ativo);
}
