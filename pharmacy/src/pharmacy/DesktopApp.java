package pharmacy;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultCaret;

import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.Panel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class DesktopApp {

	private JFrame frame;
	private JFrame frame2;
	public JTable table1;
	public JTable table2;
	public JTable table3;
	public JTable table4;
	public JTable table5;
	public JTable table6;
	public JTable table7;
	public JTable table8;
	private JTextField MedicineTextField;
	private JTextField PharmacyTextField;
	private JTextField PharmacyTextField2;
	private JComboBox CityComboBox;
	private JComboBox MonthComboBox;
	private JComboBox YearComboBox;
	private JComboBox yearComboBox2;
	private JComboBox CategoryComboBox;
	private Panel parentPanel;
	private Panel childPanel1;
	private JPanel childPanel2;
	private JButton SearchByCityButton;

	//database connection settings
	private String url="jdbc:mysql://localhost:3306/pharmacy_database";
	private	String user="root";
	private	String password="familiabodin";
	private	Connection conn=null;
	private PharmacyAPI api;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesktopApp window = new DesktopApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DesktopApp() {
		initialize();
	}
	//remove all existent tables before adding a new one
	public void RemoveTables() {
		if(table1!=null) {
			parentPanel.remove(table1);
		}
		if(table2!=null) {
			parentPanel.remove(table2);
		}
		if(table3!=null) {
			parentPanel.remove(table3);
		}
		if(table4!=null) {
			parentPanel.remove(table4);
		}
		if(table5!=null) {
			parentPanel.remove(table5);
		}
		if(table6!=null) {
			parentPanel.remove(table6);
		}
		if(table7!=null) {
			parentPanel.remove(table7);
		}
		if(table8!=null) {
			parentPanel.remove(table8);
		}
	}

	private void SearchByMedicine() {

		String medicine=MedicineTextField.getText();
		if(medicine.length()>0 ) {
			RemoveTables();
			//create a new one
			table3= new JTable();
			//configure the width for each column
			int[] width= { 60, 150, 70, 120, 80 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "CityCode", "MedicineName", "Quantity" };
			//create the table
			CreateNewTable(table3, width, names);
			//add the table to the frame
			parentPanel.add(table3);

			//create a list of pharmacies to store the results from the query
			List<PharmacyStockMedModel> list= api.GetPharmaciesFilteredByMedicine(conn, url, user, password, medicine);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The medicine name doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) table3.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				table3.setDefaultRenderer(Object.class, new FirstRowBold());
				//iterate the results from query and add them to the table
				for(PharmacyStockMedModel p : list)
				{
					Object[] row = { p.Code, p.PharmacyName, p.CitiesCityCode, p.MedicineName, p.Quantity };
					model.addRow(row);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a medicine name");
		}
	}

	private void SearchByCity() {

		String city=CityComboBox.getSelectedItem().toString();
		if(city.length()>0 ) {
			RemoveTables();
			//create a new one
			table2= new JTable();
			//configure the width for each column
			int[] width= { 40, 60, 150, 500, 120, 80 };
			//configure the name for column header
			String[] names={ "ID", "Code", "Name", "Adress", "Phone", "CityCode" };
			//create the table
			CreateNewTable(table2, width, names);
			//add the table to the frame
			parentPanel.add(table2);

			//create a list of pharmacies to store the results from the query
			List<PharmacyModel> list= api.GetPharmciesFilteredByCity(conn, url, user, password, city);
			//retrieve the table model
			DefaultTableModel model = (DefaultTableModel) table2.getModel();
			//add first row
			model.addRow(names);
			//set the first row to have bold text
			table2.setDefaultRenderer(Object.class, new FirstRowBold());

			//iterate the results from query and add them to the table
			for(PharmacyModel p : list)
			{
				Object[] row = { p.IdPharmacy, p.Code, p.PharmacyName, p.Adress, p.Phone, p.CitiesCityCode };
				model.addRow(row);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter the city");
		}
	}

	private void SearchByCityAndMedicine() {

		String city=CityComboBox.getSelectedItem().toString();
		String medicine=MedicineTextField.getText();
		if(city.length()>0 && medicine.length()>0 ) {
			RemoveTables();
			//create a new table
			table4= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 120 };
			//configure the name for column header
			String[] names={ "CityCode", "MedicineName", "TotalQuantity" };
			//create the table
			CreateNewTable(table4, width, names);
			//add the table to the frame
			parentPanel.add(table4);

			//create a list of pharmacies to store the results from the query
			List<PharmacyStockMedModel> list= api.GetPharmaciesFilteredByMedicineAndCity(conn, url, user, password, medicine, city);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The medicine name or city doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) table4.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				table4.setDefaultRenderer(Object.class, new FirstRowBold());
				//iterate the results from query and add them to the table
				for(PharmacyStockMedModel p : list)
				{
					Object[] row = { p.CitiesCityCode, p.MedicineName, p.Quantity };
					model.addRow(row);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a medicine name and a city!");
		}
	}

	private void SearchByPharmacyCategoryAndYear() {

		String pharmacy= PharmacyTextField2.getText();
		String category= CategoryComboBox.getSelectedItem().toString();
		String year= YearComboBox.getSelectedItem().toString();
		if(pharmacy.length()>0 && category.length()>0 && year.length()>0) {
			RemoveTables();
			//create a new table
			table7= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "NumberOfOrders", "CategoryName" };
			//create the table
			CreateNewTable(table7, width, names);
			//add the table to the frame
			parentPanel.add(table7);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list = api.GetOrdersFilteredByPharmacyCategoryAndYear(conn, url, user, password, pharmacy, category, year);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No results!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) table7.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				table7.setDefaultRenderer(Object.class, new FirstRowBold());
				//iterate the results from query and add them to the table
				for(OrderPharmacyMonthModel p : list)
				{
					Object[] row = { p.Code, p.PharmacyName, p.ShippingDate, p.NumberOrders, p.CategoryName };
					model.addRow(row);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a pharmacy name, a category and a year!");
		}
	}

	private void SearchByPharmacyAndMonth() {

		String month= MonthComboBox.getSelectedItem().toString();
		String pharmacy=PharmacyTextField.getText();
		if(month.length()>0 && pharmacy.length()>0 ) {
			RemoveTables();
			//create a new table
			table5= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "NumberOfOrders", "TotalSum" ,"AvgPricePerOrder" };
			//create the table
			CreateNewTable(table5, width, names);
			//add the table to the frame
			parentPanel.add(table5);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list= api.GetOrdersFilteredByPharmacyAndMonth(conn, url, user, password, pharmacy, month);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The pharmacy name doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) table5.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				table5.setDefaultRenderer(Object.class, new FirstRowBold());
				//iterate the results from query and add them to the table
				for(OrderPharmacyMonthModel p : list)
				{
					Object[] row = { p.Code, p.PharmacyName, p.ShippingDate, p.NumberOrders, p.TotalSum, p.AvgPriceOrder };
					model.addRow(row);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a pharmacy name and a month!");
		}
	}
	
	private void GetBiggestOrder() {

		String year= yearComboBox2.getSelectedItem().toString();
		
		if(year.length()>0) {
			RemoveTables();
			//create a new table
			table8 = new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "TotalSum" };
			//create the table
			CreateNewTable(table8, width, names);
			//add the table to the frame
			parentPanel.add(table8);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list= api.GetTheBiggestOrder(conn, url, user, password, year);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No results!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) table8.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				table8.setDefaultRenderer(Object.class, new FirstRowBold());
				//iterate the results from query and add them to the table
				for(OrderPharmacyMonthModel p : list)
				{
					Object[] row = { p.Code, p.PharmacyName, p.ShippingDate, p.TotalSum};
					model.addRow(row);
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a year!");
		}
	}
	
	public void CreatePanel()
	{
		api=new PharmacyAPI();
		childPanel1 = new Panel();
		childPanel1.setBounds(0, 0, 1197, 130);

		CityComboBox = new JComboBox();
		CityComboBox.setBounds(159, 21, 135, 23);
		CityComboBox.setVisible(true);
		CityComboBox.setModel(new DefaultComboBoxModel(new String[] {"TM", "CJ", "B", "IS"}));

		MedicineTextField = new JTextField();
		MedicineTextField.setBounds(159, 55, 135, 23);
		MedicineTextField.setVisible(true);
		MedicineTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		MedicineTextField.setColumns(10);


		//Search by medicine name
		JButton SearchByMedicineButton = new JButton("Search");
		SearchByMedicineButton.setBounds(332, 53, 94, 27);
		SearchByMedicineButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		SearchByMedicineButton.setVisible(true);
		SearchByMedicineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByMedicine();
			}
		});
		parentPanel.setLayout(null);

		parentPanel.add(childPanel1);
		childPanel1.setLayout(null);
		childPanel1.add(CityComboBox);
		childPanel1.add(MedicineTextField);


		//Filter stocks by medicine and city
		JButton TotalQuantitiesButton = new JButton("Show total quantities of a medicine in a city");
		TotalQuantitiesButton.setBounds(447, 21, 312, 52);
		TotalQuantitiesButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		TotalQuantitiesButton.setVisible(true);
		TotalQuantitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByCityAndMedicine();
			}
		});
		childPanel1.add(TotalQuantitiesButton);
		childPanel1.add(SearchByMedicineButton);

		JLabel lblNewLabel_1 = new JLabel("Enter a medicine:");
		lblNewLabel_1.setBounds(20, 57, 122, 18);
		childPanel1.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));

		JLabel lblNewLabel = new JLabel("Choose a city:");
		lblNewLabel.setBounds(20, 21, 101, 18);
		childPanel1.add(lblNewLabel);
		lblNewLabel.setVisible(true);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

		SearchByCityButton = new JButton("Search");
		SearchByCityButton.setBounds(332, 17, 94, 27);
		childPanel1.add(SearchByCityButton);
		SearchByCityButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JButton btnNewButton = new JButton("Farmacy with biggest order");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetBiggestOrder();
			}
		});
		btnNewButton.setBounds(973, 26, 203, 52);
		childPanel1.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Choose an year:");
		lblNewLabel_2.setBounds(785, 34, 86, 29);
		childPanel1.add(lblNewLabel_2);
		
		yearComboBox2 = new JComboBox();
		yearComboBox2.setModel(new DefaultComboBoxModel(new String[] {"2019", "2020", "2021"}));
		yearComboBox2.setBounds(881, 37, 67, 22);
		childPanel1.add(yearComboBox2);
		SearchByCityButton.setVisible(true);

		//Search by City
		SearchByCityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByCity();
			}
		});
		parentPanel.revalidate();
		parentPanel.repaint();
	}

	public void CreatePanel2() {
		parentPanel.remove(childPanel1);
		parentPanel.revalidate();
		parentPanel.repaint();
		RemoveTables();

		JLabel lblNewLabel = new JLabel("Choose a pharmacy:");
		lblNewLabel.setBounds(20, 21, 155, 18);
		childPanel2.add(lblNewLabel);
		lblNewLabel.setVisible(true);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

		PharmacyTextField = new JTextField();
		PharmacyTextField.setBounds(170, 21, 135, 23);
		PharmacyTextField.setVisible(true);
		PharmacyTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		PharmacyTextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Choose a month:");
		lblNewLabel_1.setBounds(20, 57, 155, 18);
		childPanel2.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));

		MonthComboBox = new JComboBox();
		MonthComboBox.setBounds(170, 57, 135, 23);
		MonthComboBox.setVisible(true);
		MonthComboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));

		//Search by pharmacy name
		JButton SearchByPharmacyButton = new JButton("Show total sum and avg sum/order");
		SearchByPharmacyButton.setBounds(350, 21, 312, 52);
		SearchByPharmacyButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		SearchByPharmacyButton.setVisible(true);
		SearchByPharmacyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByPharmacyAndMonth();
			}
		});
		
		childPanel2.add(PharmacyTextField);
		childPanel2.add(MonthComboBox);
		childPanel2.add(SearchByPharmacyButton);

		JLabel lblNewLabe2 = new JLabel("Choose a pharmacy:");
		lblNewLabe2.setBounds(700, 21, 155, 18);
		childPanel2.add(lblNewLabe2);
		lblNewLabe2.setVisible(true);
		lblNewLabe2.setFont(new Font("Verdana", Font.PLAIN, 14));

		PharmacyTextField2 = new JTextField();
		PharmacyTextField2.setBounds(850, 21, 135, 23);
		PharmacyTextField2.setVisible(true);
		PharmacyTextField2.setFont(new Font("Verdana", Font.PLAIN, 13));
		PharmacyTextField2.setColumns(10);

		JLabel lblNewLabel3 = new JLabel("Choose a category:");
		lblNewLabel3.setBounds(700, 57, 155, 18);
		childPanel2.add(lblNewLabel3);
		lblNewLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel3.setVisible(true);
		lblNewLabel3.setFont(new Font("Verdana", Font.PLAIN, 14));

		CategoryComboBox = new JComboBox();
		CategoryComboBox.setBounds(850, 57, 135, 23);
		CategoryComboBox.setVisible(true);
		List<CategoryModel> list= api.GetCategories(conn, url, user, password);
		String[] categories=new String[20];
		int i=0;
		for(CategoryModel p : list)
		{
			categories[i]=p.CategoryName;
			i++;
		}
		CategoryComboBox.setModel(new DefaultComboBoxModel(categories));
		
		JLabel lblNewLabel4 = new JLabel("Choose an year:");
		lblNewLabel4.setBounds(700, 90 , 155, 18);
		childPanel2.add(lblNewLabel4);
		lblNewLabel4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel4.setVisible(true);
		lblNewLabel4.setFont(new Font("Verdana", Font.PLAIN, 14));

		YearComboBox = new JComboBox();
		YearComboBox.setBounds(850, 90, 135, 23);
		YearComboBox.setVisible(true);
		YearComboBox.setModel(new DefaultComboBoxModel(new String[] {"2019", "2020", "2021"}));

		//Search by category name
		JButton SearchByCategoryButton = new JButton("Search");
		SearchByCategoryButton.setBounds(1000, 21, 100, 60);
		SearchByCategoryButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		SearchByCategoryButton.setVisible(true);
		SearchByCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByPharmacyCategoryAndYear();
			}
		});
		
		parentPanel.setLayout(null);
		
		childPanel2.setLayout(null);
		childPanel2.add(PharmacyTextField2);
		childPanel2.add(CategoryComboBox);
		childPanel2.add(YearComboBox);
		childPanel2.add(SearchByCategoryButton);
		parentPanel.add(childPanel2);
		
		ShowAllOrders();

	}

	private void ShowAllPharmacies()
	{
		RemoveTables();
		//create a new table
		table1 = new JTable();
		//configure the width for each column
		int[] width= { 40, 60, 150, 500, 120, 80 };
		//configure the name for column header
		String[] names={ "ID", "Code", "Name", "Adress", "Phone", "CityCode" };
		//create the table
		CreateNewTable(table1, width, names);
		//add the table to the frame
		parentPanel.add(table1);
		//create a list of pharmacies to store the results from the query
		List<PharmacyModel> list= api.GetPharmacies(conn, url, user, password);
		//retrieve the table model
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		//add first row
		model.addRow(names);
		//set the first row to have bold text
		table1.setDefaultRenderer(Object.class, new FirstRowBold());
		//iterate the results from query and add them to the table
		for(PharmacyModel p : list)
		{
			Object[] row = { p.IdPharmacy, p.Code, p.PharmacyName, p.Adress, p.Phone, p.CitiesCityCode };
			model.addRow(row);
		}
	}

	private void ShowAllOrders()
	{
		RemoveTables();
		//create a new table
		table6 = new JTable();
		//configure the width for each column
		int[] width= { 40, 60, 150, 150, 150, 150, 80 };
		//configure the name for column header
		String[] names={ "ID", "Code", "PharmacyName", "ShippingDate", "MedicineName", "MedicineQuantity", "Price" };
		//create the table
		CreateNewTable(table6, width, names);
		//add the table to the frame
		parentPanel.add(table6);
		//create a list of orders to store the results from the query
		List<OrderModel> list= api.GetOrders(conn, url, user, password);
		//retrieve the table model
		DefaultTableModel model = (DefaultTableModel) table6.getModel();
		//add first row
		model.addRow(names);
		//set the first row to have bold text
		table6.setDefaultRenderer(Object.class, new FirstRowBold());
		//iterate the results from query and add them to the table
		for(OrderModel p : list)
		{
			Object[] row = { p.IdOrder, p.Code, p.PharmacyName, p.ShippingDate, p.MedicineName, p.MedicineQuantity, p.Price };
			model.addRow(row);
		}
	}

	//Create a new table with the basic settings for column width and header names
	public JTable CreateNewTable(JTable table, int[] width, String[] names){

		table.setFont(new Font("Verdana", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				names
				) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		for(int i=0;i<width.length;i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
			table.getColumnModel().getColumn(i).setMaxWidth(width[i]);
		}
		table.setBounds(10, 136, 1177, 396);

		return table;
	}

	//bold the first row to see better the header
	public class FirstRowBold extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(row == 0) {
				parent.setFont(
						parent.getFont().deriveFont(Font.BOLD));
			}
			return parent;
		} 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		api=new PharmacyAPI();

		//create the frame
		frame = new JFrame();
		frame.setBounds(100, 100, 1213, 621);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//create the parent panel
		parentPanel = new Panel();
		parentPanel.setBounds(0, 39, 1197, 543);

		//add the components for the parent panel
		CreatePanel();

		//create a toolbar and add it directly to the frame
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar);
		toolBar.setBackground(new Color(0, 204, 255));
		toolBar.setBounds(0, 0, 1197, 36);

		//create button Pharmacies that display all the pharmacies from the database
		JButton PharmaciesButton = new JButton("    Pharmacies     ");
		PharmaciesButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		PharmaciesButton.setBackground(new Color(204, 255, 255));
		PharmaciesButton.setBorder(new LineBorder(Color.BLACK));

		//initialiaze the table
		ShowAllPharmacies();

		//Show all pharmacies
		PharmaciesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePanel();
				ShowAllPharmacies();
			}
		});
		toolBar.add(PharmaciesButton);

		childPanel2 = new JPanel();
		childPanel2.setBounds(0, 0, 1197, 130);

		//Remove panel and add new components
		JButton OrdersButton = new JButton("    Orders    ");
		OrdersButton.setForeground(new Color(0, 0, 0));
		OrdersButton.setBackground(new Color(204, 255, 255));
		OrdersButton.setBorder(new LineBorder(Color.BLACK));
		OrdersButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		OrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreatePanel2();
			}
		});
		toolBar.add(OrdersButton);
		frame.getContentPane().add(parentPanel);
	}


	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
