package org.jun;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.table.TableModel;


public class LibraryManagement {

	private JFrame frmLibraryManagement;
	private JTextField textSearchBookId;
	private JTextField textSearchBookTitle;
	private JTextField textSearchFullName;
	private JTextField textSearchFirstName;
	private JTextField textSearchMI;
	private JTextField textSearchLastName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryManagement window = new LibraryManagement();
					window.frmLibraryManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryManagement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryManagement = new JFrame();
		frmLibraryManagement.setTitle("Library Management");
		frmLibraryManagement.setBounds(100, 100, 691, 474);
		frmLibraryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmLibraryManagement.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmLibraryManagement.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		ButtonGroup partNameGroup = new ButtonGroup();
		
		JPanel panelSearch = new JPanel();
		tabbedPane.addTab("Book Search", null, panelSearch, null);
		panelSearch.setLayout(null);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Dialog", Font.BOLD, 13));
		lblBookId.setBounds(56, 27, 70, 19);
		panelSearch.add(lblBookId);
		
		textSearchBookId = new JTextField();
		textSearchBookId.setFont(new Font("Dialog", Font.PLAIN, 13));
		textSearchBookId.setBounds(160, 27, 167, 19);
		panelSearch.add(textSearchBookId);
		textSearchBookId.setColumns(10);
		
		JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setFont(new Font("Dialog", Font.BOLD, 13));
		lblBookTitle.setBounds(362, 29, 90, 15);
		panelSearch.add(lblBookTitle);
		
		textSearchBookTitle = new JTextField();
		textSearchBookTitle.setFont(new Font("Dialog", Font.PLAIN, 13));
		textSearchBookTitle.setBounds(483, 27, 147, 19);
		panelSearch.add(textSearchBookTitle);
		textSearchBookTitle.setColumns(10);
		

		
		textSearchFullName = new JTextField();
		textSearchFullName.setBounds(160, 56, 470, 19);
		panelSearch.add(textSearchFullName);
		textSearchFullName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(69, 83, 132, 23);
		panelSearch.add(lblFirstName);
		
		textSearchFirstName = new JTextField();
		textSearchFirstName.setBounds(160, 85, 114, 19);
		panelSearch.add(textSearchFirstName);
		textSearchFirstName.setColumns(10);
		
		JLabel lblMi = new JLabel("MI");
		lblMi.setBounds(303, 85, 36, 15);
		panelSearch.add(lblMi);
		
		textSearchMI = new JTextField();
		textSearchMI.setBounds(345, 85, 45, 19);
		panelSearch.add(textSearchMI);
		textSearchMI.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(424, 85, 90, 15);
		panelSearch.add(lblLastName);
		
		textSearchLastName = new JTextField();
		textSearchLastName.setBounds(516, 85, 114, 19);
		panelSearch.add(textSearchLastName);
		textSearchLastName.setColumns(10);
		
		JRadioButton rdbtnFullName = new JRadioButton("Full Name");
		rdbtnFullName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFullNameEnabled(true);
			}
		});
		rdbtnFullName.setSelected(true);
		rdbtnFullName.setFont(new Font("Dialog", Font.BOLD, 13));
		rdbtnFullName.setBounds(40, 54, 112, 23);
		panelSearch.add(rdbtnFullName);
		
		JRadioButton rdbtnPartsOfName = new JRadioButton("");
		rdbtnPartsOfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFullNameEnabled(false);
			}
		});
		rdbtnPartsOfName.setBounds(40, 85, 28, 23);
		panelSearch.add(rdbtnPartsOfName);
		partNameGroup.add(rdbtnFullName);
		partNameGroup.add(rdbtnPartsOfName);
		setFullNameEnabled(true);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*String query = "SELECT BOOK.Book_id, BOOK.Title, BOOK_AUTHORS.Author_name AS Authors, "
						+ " BOOK_COPIES.Branch_id, BOOK_COPIES.No_of_copies "
						+ " FROM BOOK_COPIES, BOOK, BOOK_AUTHORS "
						+ " WHERE BOOK_COPIES.Book_id = BOOK.Book_id AND BOOK_AUTHORS.Book_id = BOOK.Book_id "
						+ "  ";*/
				String query = "SELECT    B.Book_id, "
						+ 				" B.Title, "
						+ 				" (SELECT GROUP_CONCAT(Author_name SEPARATOR ', ') AS Authors "
						+ 				"  FROM BOOK B1, BOOK_AUTHORS A1  "
						+ 				"  WHERE B1.Book_id = A1.Book_id AND B.Book_id = B1.Book_id  "
						+ 				"  GROUP BY B1.Book_id), "
						+ 				" C.Branch_id, "
						+ 				" C.No_of_copies, "
						+ 				" C.No_of_copies - "
						+ 						" (SELECT COUNT(*) "
						+ 						"  FROM BOOK_LOANS L "
						+ 						"  WHERE B.Book_id = L.Book_id AND C.Branch_id = L.Branch_id) "
						+ 					" AS Available "
						+ 		" FROM BOOK B, BOOK_COPIES C, BOOK_AUTHORS A"
						+ 		" WHERE B.Book_id = C.Book_id AND A.Book_id = B.Book_id ";
				Statement stmt = DBConnector.instance().createStatement();
				
				if(fullNameEnabled) {
					String BookId = textSearchBookId.getText();	
					if(!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
						query += " AND B.Book_id LIKE '%" + BookId + "%' ";
					}
					
					String fullName = textSearchFullName.getText();	
					if(!(fullName == null || fullName.equals("") || fullName.matches("\\s+"))) {
						query += " AND A.Author_name LIKE '%" + fullName + "%' ";
					}
					
					query += " GROUP BY BOOK_COPIES.Branch_id;";
					// while(!firstName.matches("^[^\\d\\s]+$"));
					ResultSet rs = null;
					
					try {
						System.out.println(query);
						rs = stmt.executeQuery(query);
						if(null == rs) {
							return;
						}
						
						ResultsModel model = new ResultsModel();
						model.setResultSet(rs);
						tableSearch.setModel(model);
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				} else {
					
					String BookId = textSearchBookId.getText();	
					if(!(BookId == null || BookId.equals("") || BookId.matches("\\s+"))) {
						query += " AND BOOK.Book_id LIKE '%" + BookId + "%' ";
					}
					
					String firstName = textSearchFirstName.getText();	
					if(!(firstName == null || firstName.equals("") || firstName.matches("\\s+"))) {
						query += " AND Fname LIKE '%" + firstName + "%' ";
					}
					
					String MI = textSearchMI.getText();	
					if(!(MI == null || MI.equals("") || MI.matches("\\s+"))) {
						query += " AND MI LIKE '%" + MI + "%' ";
					}
					
					String lastName = textSearchLastName.getText();	
					if(!(lastName == null || lastName.equals("") || lastName.matches("\\s+"))) {
						query += " AND Lname LIKE '%" + lastName + "%' ";
					}
					
					query += " GROUP BY BOOK_COPIES.Branch_id;";
					// while(!firstName.matches("^[^\\d\\s]+$"));
					ResultSet rs = null;
					
					try {
						System.out.println(query);
						rs = stmt.executeQuery(query);
						if(null == rs) {
							return;
						}
						
						ResultsModel model = new ResultsModel();
						model.setResultSet(rs);
						tableSearch.setModel(model);
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				}
			}
		});
		btnSearch.setBounds(84, 125, 117, 25);
		panelSearch.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				ResultsModel model = (ResultsModel) tableSearch.getModel();
				model.setResultSet(null);
				//tableSearch.setModel(model);
				
			}
		});
		btnClear.setBounds(424, 125, 117, 25);
		panelSearch.add(btnClear);
		
		JScrollPane scrollPaneSearch = new JScrollPane();
		scrollPaneSearch.setBounds(12, 162, 660, 227);
		panelSearch.add(scrollPaneSearch);
		
		tableSearch = new JTable();
		scrollPaneSearch.setViewportView(tableSearch);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
	}
	
	private boolean fullNameEnabled = true;
	private JTable tableSearch;
	private void setFullNameEnabled(boolean enabled) {
		textSearchFirstName.setEnabled(!enabled);
		textSearchMI.setEnabled(!enabled);
		textSearchLastName.setEnabled(!enabled);
		textSearchFullName.setEnabled(enabled);
		fullNameEnabled = enabled;
	}
}
