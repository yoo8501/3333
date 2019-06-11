
public class test2 {
	ClientMain main;
	JPanel p_west;
	JPanel p_east;
	Choice ch_top;
	Choice ch_sub;
	JTextField t_name;
	JTextField t_price;
	Canvas can_regist;
	// Canvas can_detail;
	JButton bt_find;
	JButton bt_regist;

	// ���� ī�װ��� pk ���� �迭
	ArrayList top_list = new ArrayList();
	ArrayList sub_list = new ArrayList();
	JFileChooser chooser;
	Image regist_img;
	Image regist_img2;
	String regist_path;// ��Ͻ� ����� �̹��� ���
	String imgName;

	// JTable
	ProductTableModel model;
	JTable table;
	JScrollPane scroll;

	// �󼼺������

	Choice ch_top2;
	Choice ch_sub2;
	JTextField t_name2;
	JTextField t_price2;
	Canvas can_regist2;
	// Canvas can_detail;
	JButton bt_find2;
	JButton bt_edit;
	JButton bt_del;
	URL url;
	int product_id;//���õ� ��ǰ�� primary key;
	String img;//���� ���õ� ��ǰ�� �̹���
	File file;//���� ���õ� ����
	String userData= "C:/Users/itbank/java_developer/data";
	
	public ProductMain(ClientMain main) {
		this.main = main;
		p_west = new JPanel();
		p_east = new JPanel();
		ch_top = new Choice();
		ch_sub = new Choice();
		t_name = new JTextField();
		t_price = new JTextField();

		can_regist = new Canvas() {
			@Override
			public void paint(Graphics g) {
				// �׸� �׸��� ����

				g.drawImage(regist_img, 0, 0, 145, 145, null);

			}
		};
		bt_find = new JButton("���� ã��");
		bt_regist = new JButton("���");
		chooser = new JFileChooser("E:/java_developer/java/Project0107/res");
		table = new JTable();
		scroll = new JScrollPane(table);

		// �󼼺��� new

		p_east = new JPanel();
		ch_top2 = new Choice();
		ch_sub2 = new Choice();
		t_name2 = new JTextField();
		t_price2 = new JTextField();

		can_regist2 = new Canvas() {
			@Override
			public void paint(Graphics g) {
				// �׸� �׸��� ����

				g.drawImage(regist_img2, 0, 0, 145, 145, null);

			}
		};
		bt_find2 = new JButton("���� ã��");
		bt_edit = new JButton("����");
		bt_del = new JButton("����");

		// ���̺��� row���� Ű���
		table.setRowHeight(65);

		Dimension d = new Dimension(145, 25);

		ch_top.setPreferredSize(d);
		ch_sub.setPreferredSize(d);
		t_name.setPreferredSize(d);
		t_price.setPreferredSize(d);

		can_regist.setPreferredSize(new Dimension(145, 145));
		bt_find.setPreferredSize(d);
		bt_regist.setPreferredSize(d);

		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(can_regist);
		p_west.add(bt_find);
		p_west.add(bt_regist);
		p_west.setPreferredSize(new Dimension(170, 600));

		// ���� �г��� BorderLayout ����
		this.setLayout(new BorderLayout());
		add(p_west, BorderLayout.WEST);
		add(scroll);

		add(p_east, BorderLayout.EAST);
		p_east.add(ch_top2);
		p_east.add(ch_sub2);
		p_east.add(t_name2);
		p_east.add(t_price2);
		p_east.add(can_regist2);
		p_east.add(bt_find2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		p_east.setPreferredSize(new Dimension(170, 600));

		ch_top2.setPreferredSize(d);
		ch_sub2.setPreferredSize(d);
		t_name2.setPreferredSize(d);
		t_price2.setPreferredSize(d);

		can_regist2.setPreferredSize(new Dimension(145, 145));
		bt_find2.setPreferredSize(d);
		bt_edit.setPreferredSize(d);
		bt_del.setPreferredSize(d);
		
		
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(1300, 600));
		ch_top.addItemListener(new ItemListener() {


			public void itemStateChanged(ItemEvent e) {
				int index = ch_top.getSelectedIndex();
				Integer obj = (Integer) top_list.get(index);
				getSubList(obj,ch_sub);

			}
		});
		ch_top2.addItemListener(new ItemListener() {


			public void itemStateChanged(ItemEvent e) {
				int index = ch_top2.getSelectedIndex();
				Integer obj = (Integer) top_list.get(index);
				getSubList(obj,ch_sub2);

			}
		});
		// ��ư�� ������ ����
		bt_find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile(can_regist);
				JOptionPane.showMessageDialog(main, "����� ����� �̹������� "+img);

			}
		});
		bt_find2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile(can_regist2);
				JOptionPane.showMessageDialog(main, "����� ������ �̹������� "+img);
			}
		});
		bt_regist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				upload();
				regist();

			}
		});
		bt_del.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				if(product_id ==0) {
					JOptionPane.showMessageDialog(main, "���� ��ǰ�����ϼ���");
					return;
				}
				
				if(JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog(main, "�����Ͻðڽ��ϱ�?")) {
					if(deleteFile()) {//���ϸ��� ����� db�����;
						delete();//db�����
						selectAll();
						table.updateUI();
						regist_img2 = null;
						can_regist2.repaint();

					}
			
				}
			}
		});
		bt_edit.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(main, "�����Ͻðڽ��ϱ�?") == JOptionPane.OK_OPTION) {
					//�󼼺����� �̹������ ���������� ����ã�⿡ ���� ���õ� �̹������� �ٸ��ٸ� ������ü�� ���
					
					if(regist_img2 != null) {
						System.out.println("���ؿ�");
						upload();
					}
				
					edit();
					selectAll();
					table.updateUI();
				}
				
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = 1;
				//System.out.println(table.getValueAt(row, col));
				product_id =(Integer)table.getValueAt(row, col);//��ڽ�
				img = (String)table.getValueAt(row, 2);
				System.out.println(img);
				String top_name =(String)table.getValueAt(row, 3);
				
		
				String sub_name = (String)table.getValueAt(row, 4);
				getDetail(product_id,top_name,sub_name);
			}
		});
		// ���̽� ������Ʈ�� �� ä���
		getTopList();
		table.setModel(model = new ProductTableModel());
		selectAll();

	}

	// �ֻ��� ī�װ� ���ϱ�
	public void getTopList() {

		PreparedStatement pstmt = null;
		Connection con = main.getCon();
		ResultSet rs = null;
		try {
			String sql = "select * from topcategory";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// rs�� ������ choice�� �ű��;

			while (rs.next()) {

				ch_top.add(rs.getString("name"));
				ch_top2.add(rs.getString("name"));
				// pk�� ����
				// getInt�� int���� ��ȯ �ϰ� add�޼���� Object���� �μ���
				// �־���ϹǷ�, ���� ���� ���� �ʾ� �ַ��� ������ϴµ�
				// �ڹٿ����� �⺻�ڷ����� ����Ŭ������ �ڵ� ����ȯ�� �����Ѵ�
				// Integer -->int(unboxing)
				top_list.add(rs.getInt("topcategory_id"));
				//ch_top.select("123");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// ���� ī�װ� ���ϱ�
	public void getSubList(int topcategory_id, Choice choice) {
		String sql = "select * from subcategory where topcategory_id = '" + (topcategory_id) + "'";
		Connection con = main.getCon();

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			choice.removeAll();
			sub_list.removeAll(sub_list);
			// rs.beforeFirst();
			while (rs.next()) {
				choice.add(rs.getString("name"));
				sub_list.add(rs.getInt("subcategory_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// System.out.println(sql);
	}

	public void openFile(Canvas can) {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			regist_path = file.getAbsolutePath();
			String str =    file.getAbsolutePath();
			System.out.println(str);
			ImageIcon icon = new ImageIcon(str);
			regist_img = icon.getImage();
			regist_img2 = regist_img;
			can.repaint();
			//img = file.getName();
			img = System.currentTimeMillis() + "." + StringUtil.getExt(regist_path);

		}
	}

	// �̹��� Ư�� ��ġ�� ����(���̾��ٸ� ���ε�� ����);
	public void upload() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		img= System.currentTimeMillis() + "." + StringUtil.getExt(regist_path);
		try {

			fis = new FileInputStream(regist_path);
			fos = new FileOutputStream("C:/Users/itbank/java_developer/data"+File.separator+ img);
			System.out.println("C:/Users/itbank/java_developer/data"+File.separator+ img);
			byte[] b = new byte[1024];
			int data = -1;
			while (true) {

				data = fis.read(b);// �Է�
			if (data == -1)String fileName
					break;
				fos.write(b);// ���

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void regist() {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		// ResultSet rs =null;
		int index = ch_sub.getSelectedIndex();
		int subcategory_id = (Integer) sub_list.get(index + 1);
		String name = t_name.getText();
		String price = t_price.getText();

		// System.out.println(imgName);
		String sql = "insert into product(product_id,subcategory_id,product_name,price,img)";
		sql += "values(seq_product.nextval," + subcategory_id + ",'" + name + "'," + price + ",'" + img + "')";
		System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				JOptionPane.showMessageDialog(this, "��ϼ���");
				selectAll();
			} else {
				JOptionPane.showMessageDialog(this, "��Ͻ���");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// ����ǰ ��������(ī�װ��� ��ǰ���̺��� ����)
	public void selectAll() {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sb = new StringBuffer();

		sb.append("select t.topcategory_id,t.name as top_name");
		sb.append(" ,s.subcategory_id, s.name as sub_name");
		sb.append(" ,product_id, product_name, price, img");
		sb.append(" from topcategory t, subcategory s, product p");
		sb.append(" where t.topcategory_id = s.topcategory_id");
		sb.append(" and s.subcategory_id = p.subcategory_id");

		System.out.println(sb);

		try {
			// ��ũ�� ���� rs
			pstmt = con.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();// rs�� ��ġ�� ���ڵ� ��ȣ
			// rs�� �̿��Ͽ� �������迭�� ����
			Object[][] data = new Object[total][model.columnTitle.length];
			rs.beforeFirst();// ����ġ

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][1] = rs.getInt("product_id");// �����ڵ�
				data[i][2] = rs.getString("img");
				data[i][3] = rs.getString("top_name");
				data[i][4] = rs.getString("sub_name");
				data[i][5] = rs.getString("product_name");
				data[i][6] = rs.getString("price");
			}

			// model�� �������迭�� ��� ������� �迭�� ��ü
			model.data = data;
			table.updateUI();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getDetail(int product_id, String top_name,String sub_name) {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("����� ���Ե� ��ǰ�� id : "+product_id+"���� ī�װ�����"+top_name);
		StringBuffer sb = new StringBuffer();
		sb.append("select s.subcategory_id as subcategory_id, s.topcategory_id as topcategory_id");
		sb.append(", product_id, product_name, price, img");
		sb.append(" from subcategory s,product p");
		sb.append(" where s.subcategory_id = p.subcategory_id");
		sb.append(" and product_id ="+product_id);
		System.out.println(sb.toString());
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {//�Ѱ��̱������� Ŀ���� �������Ѵ�.
				//����ī�װ� ���õǰ�
				for(int i=0;i<top_list.size();i++) {
				
					if(ch_top2.getItem(i).equals(top_name)) {
						System.out.println(i+"��°���� ��ġ�մϴ�");
						ch_top2.select(i);
						
						getSubList((Integer)top_list.get(i), ch_sub2);
					};
				}
				for(int i=0;i<sub_list.size();i++) {
					if(ch_sub2.getItem(i).equals(sub_name)) {
						ch_sub2.select(i);
					}
				}
				//��ǰ��
				t_name2.setText(rs.getString("product_name"));
				t_price2.setText(rs.getString("price"));
				//url = this.getClass().getClassLoader().getResource(rs.getString("img"));
				ImageIcon icon = new ImageIcon(userData + File.separator+ rs.getString("img"));
				regist_img2 = icon.getImage();
				can_regist2.repaint();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*
		select s.subcategory_id, s.topcategory_id
		, product_id, product_name, price, img
		from subcategory s,product p
		where s.subcategory_id = p.subcategory_id
		and product_id = pk
		 */
	}
	public boolean deleteFile() {
		boolean result = false;
		File file = new File(userData +File.separator+img);
		System.out.println(file.getAbsolutePath());
		result =file.delete();
		return result;
	}
	//���õ�, �������Ѱ� ����
	public void delete() {
		
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		String sql = "delete from product where product_id ="+product_id;
		//System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);
			//DML ���࿡ ���� ������ ���� ���ڵ� ���� ��ȯ
			int result = pstmt.executeUpdate();
			if(result ==0) {
				JOptionPane.showMessageDialog(main,"����");
			}else {
				JOptionPane.showMessageDialog(main,"��������");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	//�����޼���
	public void edit() {
		Connection con = main.getCon();
		PreparedStatement pstmt = null;
		//������ ����� ���ε� ������ ����ϸ� ���� ����� �ȴ�.!!\
		//���ε� ������ �������� �����ϰ� �����Ͽ� ������ ����Ű�� ����
		//���ε� ������ �����ϴ� ������ ���ఴü�� PreparedStatement
		StringBuffer sb = new StringBuffer();
		sb.append("update product set subcategory_id =?");
		sb.append(", product_name = ?");
		sb.append(", price = ?");
		sb.append(", img = ?");
		sb.append(" where product_id =?");
		try {
			pstmt = con.prepareStatement(sb.toString());
			//?�� ��ü�ߴ� ���ε� �������� ����������Ѵ�.
			pstmt.setInt(1, (int)sub_list.get(ch_sub2.getSelectedIndex()));//subcategory_id
			pstmt.setString(2, t_name2.getText());//product_name
			pstmt.setInt(3, Integer.parseInt(t_price2.getText()));//price
			pstmt.setString(4, img);//img
			pstmt.setInt(5, product_id);//product_id
			int result = pstmt.executeUpdate();
			if(result>0) {
				JOptionPane.showMessageDialog(main, "��������");
				System.out.println(t_price2.getText());
			}else {
				JOptionPane.showMessageDialog(main, "����");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		
	}
}
