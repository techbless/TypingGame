import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class EditPanel extends JPanel {
	private JTextField input;
	WordSource wordSource;
	JList<String> words;
	
	public EditPanel() {
		setLayout(new BorderLayout());
		
		wordSource = WordSource.getInstance();
		words = new JList<String>(wordSource.getWords());
		JScrollPane list = new JScrollPane(words);
		list.setPreferredSize(new Dimension(200, 300));
		words.setBackground(Color.GRAY);
		add(list, BorderLayout.CENTER);
		
		add(new InputPanel(), BorderLayout.SOUTH);
		
		
		words.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				@SuppressWarnings("unchecked")
				JList<String> source = (JList<String>)e.getSource();
				
				// 안하면 에러뜸
				if(source.getValueIsAdjusting()) {
					input.setText(source.getSelectedValue().toString());
				}
			}
			
		});
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.DARK_GRAY);
			input = new JTextField(12);
			input.setHorizontalAlignment(JTextField.CENTER);
			add(input);			
			
			JButton btn = new JButton("추가/삭제");
			btn.setPreferredSize(new Dimension(90, input.getPreferredSize().height));
			add(btn);
			
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					wordSource.update(input.getText());
					words.updateUI();
					words.clearSelection();	
					input.setText("");
				}
			});
		}
	}
}