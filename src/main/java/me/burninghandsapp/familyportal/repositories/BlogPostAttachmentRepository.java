package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostAttachmentRepository extends JpaRepository<BlogPostAttachment, Integer> {
}