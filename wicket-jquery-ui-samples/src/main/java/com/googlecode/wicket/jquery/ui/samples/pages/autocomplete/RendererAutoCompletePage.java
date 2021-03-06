package com.googlecode.wicket.jquery.ui.samples.pages.autocomplete;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.GenresDAO;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;

public class RendererAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public RendererAutoCompletePage()
	{
		this.init();
	}

	private void init()
	{
		// Model //
		final IModel<Genre> model = new Model<Genre>();

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete (note that Genre does not overrides #toString()) //
		form.add(new AutoCompleteTextField<Genre>("autocomplete", model, new TextRenderer<Genre>("fullName")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				List<Genre> choices = new ArrayList<Genre>();
				int count = 0;

				for (Genre genre: GenresDAO.all())
				{
					if (genre.getName().toLowerCase().contains(input.toLowerCase()))
					{
						choices.add(genre);

						if (++count == 20) { break; } //limits the number of results
					}
				}

				return choices;
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				Genre genre = this.getModelObject();

				info("Your favorite rock genre is: " + genre.getName());
				target.add(feedback);
			}
		});
	}
}
