package models;
import java.util.*;

/**
 * A {@link Entry} containing a question as <code>content</code> and {@link Answer}s.  
 * 
 * @author Simon Marti
 * @author Mirco Kocher
 *
 */
public class Question extends Entry {

	private IDTable<Answer> answers;
	private int id;
	
	private static IDTable<Question> questions = new IDTable();
	
	/**
	 * Create a Question.
	 * @param owner the {@link User} who posted the <code>Question</code>
	 * @param content the question
	 */
	public Question(User owner, String content) {
		super(owner, content);
		this.answers = new IDTable<Answer>();
		this.id = addQuestion(this);
	}

	/**
	 * Unregisters all {@link Answer}s, {@link Vote}s and itself.
	 */
	@Override
	public void unregister() {
		Iterator<Answer> it = this.answers.iterator();
		this.answers = new IDTable<Answer>();
		while(it.hasNext()) {
			it.next().unregister();
		}
		removeQuestion(this.id);
		this.unregisterVotes();
		this.unregisterUser();
	}
	
	/**
	 * Unregisters a deleted {@link Answer}.
	 * @param answer the {@link Answer} to unregister
	 */
	public void unregister(Answer answer) {
		this.answers.remove(answer.id());
	}

	/**
	 * Post a {@link Answer} to a <code>Question</code>
	 * @param user the {@link User} posting the {@link Answer}
	 * @param content the answer
	 * @return an {@link Answer}
	 */
	public Answer answer(User user, String content) {
		Answer answer = new Answer(this.answers.nextID(), user, this, content);
		this.answers.add(answer);
		return answer;
	}
	
	/**
	 * Checks if a {@link Answer} belongs to a <code>Question</code>
	 * @param answer the {@link Answer} to check
	 * @return true if the {@link Answer} belongs to the <code>Question</code>
	 */
	public boolean hasAnswer(Answer answer) {
		return this.answers.contains(answer);
	}
	
	public int id() {
		return this.id;
	}


	public static Collection questions() {
		return questions.list();
	}
	
	private static int addQuestion(Question question) {
		return questions.add(question);
	}
	
	private static void removeQuestion(int id) {
		questions.remove(id);
	}

	public static Question get(int id) {
		return questions.get(id);
	}

	public Collection<Answer> answers() {
		return this.answers.list();
	}

	public Entry getAnswer(int id) {
		return this.answers.get(id);
	}

}
