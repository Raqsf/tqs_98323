package io.cucumber.skeleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
 
public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDateTime iso8601Date(String year, String month, String day){
		return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
	}

	@Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
	public void addNewBook(final String title, final String author, final LocalDateTime published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
 
	@When("the customer searches for books published between {int} and {int}")
	public void setSearchParameters(final int from, final int to) {
		result = library.findBooks(from, to);
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}

	@When("I search for books by title {string}")
	public void i_search_for_books_by_title(String title) {
		result = library.findBooksByTitle(title);
	}
	@Then("I should find {int} book")
	public void i_should_find_book(int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}

	@Given("I have the following books in the store")
	public void i_have_the_following_books_in_the_store(DataTable dataTable) {
		List<Map<String, String>> books = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> columns : books) {
			String[] date = columns.get("published").split("-");
			LocalDateTime published = iso8601Date(date[0], date[1], date[2]);
			library.addBook(new Book(columns.get("title"), columns.get("author"), published));
		}
	}
	@When("I search for books by author {string}")
	public void i_search_for_books_by_author_tim_tomson(final String author) {
		result = library.findBooksByAuthor(author);
	}
	@Then("I find {int} book")
	public void i_find_book(int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}
}