package io.cucumber.skeleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
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

	/* @Given("a book with the title {string}, written by {string}, published in {int}-{int}-{int}")
	public void a_book_with_the_title_written_by_published_in(String string, String string2, int int1, int int2, int int3) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("HERE");
	} */
/* 
	@When("the customer searches for books published between {int} and {int}")
	public void the_customer_searches_for_books_published_between_and(int int1, int int2) {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("HERE");
	} */
 
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
}