package io.cucumber.skeleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
 
public class Library {
	private final List<Book> store = new ArrayList<>();
 
	public void addBook(final Book book) {
		store.add(book);
	}
 
	public List<Book> findBooks(final int from, final int to) {
		return store.stream().filter(book -> {
			return book.getPublished().getYear() >= from && book.getPublished().getYear() <= to;
		}).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
	}

	public List<Book> findBooksByTitle(String title) {
		return store.stream().filter(book -> {
			return book.getTitle().equals(title);
		}).collect(Collectors.toList());
	}

	public List<Book> findBooksByAuthor(String author) {
		return store.stream().filter(book -> {
			return book.getAuthor().equals(author);
		}).collect(Collectors.toList());
	}
}