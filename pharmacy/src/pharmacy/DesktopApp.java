package pharmacy;

import java.awt.EventQueue;
import java.sql.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import java.awt.Panel;
import javax.swing.SwingConstants;

public class DesktopApp {

	private JFrame Frame;
	public JTable Table1;
	public JTable Table2;
	public JTable Table3;
	public JTable Table4;
	public JTable Table5;
	public JTable Table6;
	public JTable Table7;
	public JTable Table8;
	private JTextField MedicineTextField;
	private JTextField PharmacyTextField;
	private JTextField PharmacyTextField2;
	private JComboBox CityComboBox;
	private JComboBox MonthComboBox;
	private JComboBox YearComboBox;
	private JComboBox YearComboBox2;
	private JComboBox CategoryComboBox;
	private Panel ParentPanel;
	private Panel ChildPanel1;
	private JPanel ChildPanel2;
	private JButton SearchByCityButton;

	//database Connection settings
	private String Url="jdbc:mysql://localhost:3306/pharmacy_database";
	private	String User="root";
	private	String Password="familiabodin";
	private	Connection Conn=null;
	private PharmacyAPI Api;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesktopApp window = new DesktopApp();
					window.Frame.setVisible(true);
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
		if(Table1!=null) {
			ParentPanel.remove(Table1);
		}
		if(Table2!=null) {
			ParentPanel.remove(Table2);
		}
		if(Table3!=null) {
			ParentPanel.remove(Table3);
		}
		if(Table4!=null) {
			ParentPanel.remove(Table4);
		}
		if(Table5!=null) {
			ParentPanel.remove(Table5);
		}
		if(Table6!=null) {
			ParentPanel.remove(Table6);
		}
		if(Table7!=null) {
			ParentPanel.remove(Table7);
		}
		if(Table8!=null) {
			ParentPanel.remove(Table8);
		}
	}

	private void SearchByMedicine() {

		String medicine=MedicineTextField.getText();
		if(medicine.length()>0 ) {
			RemoveTables();
			//create a new one
			Table3= new JTable();
			//configure the width for each column
			int[] width= { 60, 150, 70, 120, 80 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "CityCode", "MedicineName", "Quantity" };
			//create the table
			CreateNewTable(Table3, width, names);
			//add the table to the Frame
			ParentPanel.add(Table3);

			//create a list of pharmacies to store the results from the query
			List<PharmacyStockMedModel> list= Api.GetPharmaciesFilteredByMedicine(Conn, Url, User, Password, medicine);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The medicine name doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) Table3.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				Table3.setDefaultRenderer(Object.class, new FirstRowBold());
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
			Table2= new JTable();
			//configure the width for each column
			int[] width= { 40, 60, 150, 500, 120, 80 };
			//configure the name for column header
			String[] names={ "ID", "Code", "Name", "Adress", "Phone", "CityCode" };
			//create the table
			CreateNewTable(Table2, width, names);
			//add the table to the Frame
			ParentPanel.add(Table2);

			//create a list of pharmacies to store the results from the query
			List<PharmacyModel> list= Api.GetPharmciesFilteredByCity(Conn, Url, User, Password, city);
			//retrieve the table model
			DefaultTableModel model = (DefaultTableModel) Table2.getModel();
			//add first row
			model.addRow(names);
			//set the first row to have bold text
			Table2.setDefaultRenderer(Object.class, new FirstRowBold());

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
			Table4= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 120 };
			//configure the name for column header
			String[] names={ "CityCode", "MedicineName", "TotalQuantity" };
			//create the table
			CreateNewTable(Table4, width, names);
			//add the table to the Frame
			ParentPanel.add(Table4);

			//create a list of pharmacies to store the results from the query
			List<PharmacyStockMedModel> list= Api.GetPharmaciesFilteredByMedicineAndCity(Conn, Url, User, Password, medicine, city);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The medicine name or city doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) Table4.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				Table4.setDefaultRenderer(Object.class, new FirstRowBold());
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
			Table7= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "NumberOfOrders", "CategoryName" };
			//create the table
			CreateNewTable(Table7, width, names);
			//add the table to the Frame
			ParentPanel.add(Table7);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list = Api.GetOrdersFilteredByPharmacyCategoryAndYear(Conn, Url, User, Password, pharmacy, category, year);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No results!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) Table7.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				Table7.setDefaultRenderer(Object.class, new FirstRowBold());
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
			Table5= new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "NumberOfOrders", "TotalSum" ,"AvgPricePerOrder" };
			//create the table
			CreateNewTable(Table5, width, names);
			//add the table to the Frame
			ParentPanel.add(Table5);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list= Api.GetOrdersFilteredByPharmacyAndMonth(Conn, Url, User, Password, pharmacy, month);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "The pharmacy name doesn't exist!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) Table5.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				Table5.setDefaultRenderer(Object.class, new FirstRowBold());
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

		String year= YearComboBox2.getSelectedItem().toString();
		
		if(year.length()>0) {
			RemoveTables();
			//create a new table
			Table8 = new JTable();
			//configure the width for each column
			int[] width= { 80, 150, 150, 150 };
			//configure the name for column header
			String[] names={ "Code", "PharmacyName", "ShippingDate", "TotalSum" };
			//create the table
			CreateNewTable(Table8, width, names);
			//add the table to the Frame
			ParentPanel.add(Table8);

			//create a list of orders to store the results from the query
			List<OrderPharmacyMonthModel> list= Api.GetTheBiggestOrder(Conn, Url, User, Password, year);
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No results!");
			}
			else {
				//retrieve the table model
				DefaultTableModel model = (DefaultTableModel) Table8.getModel();
				//add first row
				model.addRow(names);
				//set the first row to have bold text
				Table8.setDefaultRenderer(Object.class, new FirstRowBold());
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
		Api=new PharmacyAPI();
		ChildPanel1 = new Panel();
		ChildPanel1.setBounds(0, 0, 1197, 130);

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
		ParentPanel.setLayout(null);

		ParentPanel.add(ChildPanel1);
		ChildPanel1.setLayout(null);
		ChildPanel1.add(CityComboBox);
		ChildPanel1.add(MedicineTextField);


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
		ChildPanel1.add(TotalQuantitiesButton);
		ChildPanel1.add(SearchByMedicineButton);

		JLabel lblNewLabel_1 = new JLabel("Enter a medicine:");
		lblNewLabel_1.setBounds(20, 57, 122, 18);
		ChildPanel1.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 14));

		JLabel lblNewLabel = new JLabel("Choose a city:");
		lblNewLabel.setBounds(20, 21, 101, 18);
		ChildPanel1.add(lblNewLabel);
		lblNewLabel.setVisible(true);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

		SearchByCityButton = new JButton("Search");
		SearchByCityButton.setBounds(332, 17, 94, 27);
		ChildPanel1.add(SearchByCityButton);
		SearchByCityButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JButton btnNewButton = new JButton("Farmacy with biggest order");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetBiggestOrder();
			}
		});
		btnNewButton.setBounds(973, 26, 203, 52);
		ChildPanel1.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Choose an year:");
		lblNewLabel_2.setBounds(785, 34, 86, 29);
		ChildPanel1.add(lblNewLabel_2);
		
		YearComboBox2 = new JComboBox();
		YearComboBox2.setModel(new DefaultComboBoxModel(new String[] {"2019", "2020", "2021"}));
		YearComboBox2.setBounds(881, 37, 67, 22);
		ChildPanel1.add(YearComboBox2);
		SearchByCityButton.setVisible(true);

		//Search by City
		SearchByCityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchByCity();
			}
		});
		ParentPanel.revalidate();
		ParentPanel.repaint();
	}

	public void CreatePanel2() {
		ParentPanel.remove(ChildPanel1);
		ParentPanel.revalidate();
		ParentPanel.repaint();
		RemoveTables();

		JLabel lblNewLabel = new JLabel("Choose a pharmacy:");
		lblNewLabel.setBounds(20, 21, 155, 18);
		ChildPanel2.add(lblNewLabel);
		lblNewLabel.setVisible(true);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

		PharmacyTextField = new JTextField();
		PharmacyTextField.setBounds(170, 21, 135, 23);
		PharmacyTextField.setVisible(true);
		PharmacyTextField.setFont(new Font("Verdana", Font.PLAIN, 13));
		PharmacyTextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Choose a month:");
		lblNewLabel_1.setBounds(20, 57, 155, 18);
		ChildPanel2.add(lblNewLabel_1);
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
		
		ChildPanel2.add(PharmacyTextField);
		ChildPanel2.add(MonthComboBox);
		ChildPanel2.add(SearchByPharmacyButton);

		JLabel lblNewLabe2 = new JLabel("Choose a pharmacy:");
		lblNewLabe2.setBounds(700, 21, 155, 18);
		ChildPanel2.add(lblNewLabe2);
		lblNewLabe2.setVisible(true);
		lblNewLabe2.setFont(new Font("Verdana", Font.PLAIN, 14));

		PharmacyTextField2 = new JTextField();
		PharmacyTextField2.setBounds(850, 21, 135, 23);
		PharmacyTextField2.setVisible(true);
		PharmacyTextField2.setFont(new Font("Verdana", Font.PLAIN, 13));
		PharmacyTextField2.setColumns(10);

		JLabel lblNewLabel3 = new JLabel("Choose a category:");
		lblNewLabel3.setBounds(700, 57, 155, 18);
		ChildPanel2.add(lblNewLabel3);
		lblNewLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel3.setVisible(true);
		lblNewLabel3.setFont(new Font("Verdana", Font.PLAIN, 14));

		CategoryComboBox = new JComboBox();
		CategoryComboBox.setBounds(850, 57, 135, 23);
		CategoryComboBox.setVisible(true);
		List<CategoryModel> list= Api.GetCategories(Conn, Url, User, Password);
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
		ChildPanel2.add(lblNewLabel4);
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
		
		ParentPanel.setLayout(null);
		
		ChildPanel2.setLayout(null);
		ChildPanel2.add(PharmacyTextField2);
		ChildPanel2.add(CategoryComboBox);
		ChildPanel2.add(YearComboBox);
		ChildPanel2.add(SearchByCategoryButton);
		ParentPanel.add(ChildPanel2);
		
		ShowAllOrders();

	}

	private void ShowAllPharmacies()
	{
		RemoveTables();
		//create a new table
		Table1 = new JTable();
		//configure the width for each column
		int[] width= { 40, 60, 150, 500, 120, 80 };
		//configure the name for column header
		String[] names={ "ID", "Code", "Name", "Adress", "Phone", "CityCode" };
		//create the table
		CreateNewTable(Table1, width, names);
		//add the table to the Frame
		ParentPanel.add(Table1);
		//create a list of pharmacies to store the results from the query
		List<PharmacyModel> list= Api.GetPharmacies(Conn, Url, User, Password);
		//retrieve the table model
		DefaultTableModel model = (DefaultTableModel) Table1.getModel();
		//add first row
		model.addRow(names);
		//set the first row to have bold text
		Table1.setDefaultRenderer(Object.class, new FirstRowBold());
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
		Table6 = new JTable();
		//configure the width for each column
		int[] width= { 40, 60, 150, 150, 150, 150, 80 };
		//configure the name for column header
		String[] names={ "ID", "Code", "PharmacyName", "ShippingDate", "MedicineName", "MedicineQuantity", "Price" };
		//create the table
		CreateNewTable(Table6, width, names);
		//add the table to the Frame
		ParentPanel.add(Table6);
		//create a list of orders to store the results from the query
		List<OrderModel> list= Api.GetOrders(Conn, Url, User, Password);
		//retrieve the table model
		DefaultTableModel model = (DefaultTableModel) Table6.getModel();
		//add first row
		model.addRow(names);
		//set the first row to have bold text
		Table6.setDefaultRenderer(Object.class, new FirstRowBold());
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
	 * Initialize the contents of the Frame.
	 */
	private void initialize() {

		Api=new PharmacyAPI();

		//create the Frame
		Frame = new JFrame();
		Frame.setBounds(100, 100, 1213, 621);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.getContentPane().setLayout(null);

		//create the parent panel
		ParentPanel = new Panel();
		ParentPanel.setBounds(0, 39, 1197, 543);

		//add the components for the parent panel
		CreatePanel();

		//create a toolbar and add it directly to the Frame
		JToolBar toolBar = new JToolBar();
		Frame.getContentPane().add(toolBar);
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

		ChildPanel2 = new JPanel();
		ChildPanel2.setBounds(0, 0, 1197, 130);

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
		Frame.getContentPane().add(ParentPanel);
	}


	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
