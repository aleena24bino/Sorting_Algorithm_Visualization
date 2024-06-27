import java.awt.*;    
import javax.swing.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

class LeftLine extends JPanel{
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting

        // Cast Graphics to Graphics2D for better control
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for better graphics quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        // Set the line color
        g2d.setColor(Color.BLACK);
        // Draw a line from (x1, y1) to (x2, y2)
        g2d.drawLine(0, getHeight(), getWidth(),0);
        //g2d.drawLine(11, 20,300,400);
    }
}

class RightLine extends JPanel{
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting

        // Cast Graphics to Graphics2D for better control
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for better graphics quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));
        // Set the line color
        g2d.setColor(Color.BLACK);
        // Draw a line from (x1, y1) to (x2, y2)
        g2d.drawLine( 0, 0, getWidth(), getHeight());
        //g2d.drawLine(11, 20,300,400);
    }
}

class Arr_Box
{
    public int[] pos;
    public int[][] pos_2d;
    public int l;
    public Color color_background;
    public Color color_foreground;
    
    public Arr_Box(Color background,Color foreground,int... a)
    {
        color_background = background;
        color_foreground = foreground;
        pos=a;
        l=a.length;
    }
    public Arr_Box(int row,Color background,Color foreground,int... a)
    {
        color_background = background;
        color_foreground = foreground;
        l=a.length;
        pos_2d = new int[row][a.length];
        for (int j = 0; j < a.length; j++) {
            pos_2d[row-1][j]=a[j];
        }
    }
    public Arr_Box(Color background,Color foreground,int[]... a)
    {
        color_background = background;
        color_foreground = foreground;
        pos_2d=a;
    }
}

class GUI_Display_Arr extends JFrame
{
    public JPanel panels;
    public int panels_l;
    public JPanel[] displays;
    public int displays_l;
    public int[][] arr_2d_format;
    public JButton start;
    public JButton stop;
    public Timer Temp_timer;
    public int cur_round;
    public int stop_round;

    public GUI_Display_Arr()
    {
        panels= new JPanel();
        panels_l=0;
        displays_l=0;
        start = new JButton("Start");
        stop = new JButton("Stop");
        Temp_timer = new Timer(1000, null);
        cur_round = 0;
        stop_round=0;
    }

    public int num_l_2d(int arr[][])
    {
        int n = 1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if(n<String.valueOf(arr[i][j]).length()){
                    n=String.valueOf(arr[i][j]).length();
                }                
            }
        }
        return n;
    }

    public int exp(int n)
    {
        int x=1;
        for (int i = 0; i < n; i++) {
            x=x*2;
        }
        return x;
    }

    public int depth(int n)
    {
        int x=1;
        while (n>1) {
            n=n/2;
            x++;
        }
        return x;
    }

    public int[][] make_2d_arr(int row, int[] arr)
    {
        int col = arr.length;
        int[][] temp_arr = new int[depth(col)][col];
        for (int i = 0; i < temp_arr.length; i++) {
            if (i==row) {
                for (int j = 0; j < temp_arr[0].length; j++) {
                    temp_arr[i][j] = arr[j];
                }
            }
        }
        return temp_arr;
    }

    public void cut_arr(int row,int col,int i,int j){
        if(i<j){
            arr_2d_format[row][col]=j-i+1;
            int mid=(i+j)/2;
            cut_arr(row+1,2*col,i,mid);
            cut_arr(row+1,2*col+1,mid+1,j);
        }else if(row<depth(arr_2d_format[0].length)){
            arr_2d_format[row][col]=j-i+1;
        }
    }

    public int num_l(int arr[])
    {
        int n = 1;
        for (int i = 0; i < arr.length; i++) {
            if(n<String.valueOf(arr[i]).length()){
                n=String.valueOf(arr[i]).length();
            }
        }
        return n;
    }

    public void addText(String str)
    {
        JLabel label = new JLabel(str);
        JPanel panel = new JPanel();
        label.setFont(new Font("Arial", Font.BOLD, 17));
        panel.add(label);
        panels.add(panel);
        panels_l++;
    }

    public void addText(String str,Color c)
    {
        JLabel label = new JLabel(str);
        JPanel panel = new JPanel();
        JTextField txt = new JTextField(5);
        label.setFont(new Font("Arial", Font.BOLD, 17));
        txt.setBorder(BorderFactory.createLineBorder(Color.black,3));    
        txt.setBackground(c);
        panel.add(label);
        panel.add(txt);
        panels.add(panel);
        panels_l++;
    }
    
    public void addArr(int[] arr,int num_l,Arr_Box... b)
    {
        // int num_l = num_l(arr);
        JPanel panel = new JPanel();
        JPanel tempPanel = new JPanel();

        int[] pos = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            pos[i]=i+1;
        }
        Arr_Box temp = new Arr_Box(Color.white,Color.black, pos);

        for (int i = 0; i < arr.length; i++) {
            temp = new Arr_Box(Color.white,Color.black, pos);
            for (Arr_Box box : b)
            {
                for (int j = 0; j < box.pos.length; j++) {
                    if (box.pos[j]==i+1) {
                        temp=box;
                    }
                }
                
            }
            JTextField txt = new JTextField(""+arr[i],num_l+2);   
            txt.setBorder(BorderFactory.createLineBorder(Color.black,3));         
            txt.setFont(new Font("Arial", Font.BOLD, 15));
            txt.setHorizontalAlignment(JTextField.CENTER);
            txt.setBackground(temp.color_background);
            txt.setForeground(temp.color_foreground);
            tempPanel.add(txt);
        }


        tempPanel.setLayout(new GridLayout(1, arr.length, 5, 25));
        panel.add(tempPanel);
        panels.add(panel);
        panels_l++;
    }

    public void addArr(int[] arr,Arr_Box... b)
    {
        addArr(arr, num_l(arr), b);
    }

    public void addArr_color(int low, int high,int num_l,int[] arr)
    {
        Color[] colors = {Color.red,Color.blue,Color.green,Color.yellow,Color.pink};
        Arr_Box[] boxes = new Arr_Box[high+1-low];
        Arr_Box box;
        int n;
        for (int i = low; i < high+1 ; i++) {
            n=i%colors.length;
            if (colors[n]==Color.red || colors[n]==Color.blue) {
                box = new Arr_Box(colors[n], Color.white, i+1);
            }else{
                box = new Arr_Box(colors[n], Color.black, i+1); 
            }
            boxes[i-low]=box;
        }
        addArr(arr,num_l,boxes);
    }

    public void addArr_color(int low, int high,int[] arr)
    {
        addArr_color( low, high, num_l(arr), arr);
    }    

    public void addArr_color(int[] arr)
    {
        addArr_color(0, arr.length-1, arr);
    }

    public void addArr_2d(int[][] arr,Arr_Box... b)
    {
        JPanel panel = new JPanel();
        JPanel tempPanel = new JPanel();
        JPanel rowPanel = new JPanel();
        JPanel colPanel = new JPanel();
        int row = arr.length;
        int col = arr[0].length;
        int num_l = num_l_2d(arr);
        arr_2d_format = new int[arr.length][arr[0].length];
        cut_arr(0, 0, 0, arr[0].length-1);
        int pos[][] = new int[row][col];

        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < pos[0].length; j++) {
                pos[i][j]=i+1;
            }
        }

        Arr_Box temp = new Arr_Box(Color.white,Color.black, pos);

        for (int i = 0; i < row; i++) {
            rowPanel = new JPanel();
            int j = 0;
            while (j < col) {
                for (int k : arr_2d_format[i]) {
                    colPanel = new JPanel();
                    if (k==0) {
                        break;
                    }
                    for (int x = 0; x < k && j<col; x++) { 
                        temp = new Arr_Box(Color.white,Color.black, pos); 
                        for (Arr_Box box : b)
                        {
                            for (int g = 0; g < box.pos_2d.length; g++) {
                                for (int h = 0; h < box.pos_2d[0].length; h++) {
                                    if (box.pos_2d[g][h]==j+1 && g==i) {
                                        temp=box;
                                    }
                                }
                            }

                        }           
                        JPanel txt_panel = new JPanel();           
                        JTextField txt = new JTextField(""+arr[i][j],num_l+1);   
                        txt.setBorder(BorderFactory.createLineBorder(Color.black,3));         
                        txt.setFont(new Font("Arial", Font.BOLD, 15));
                        txt.setBackground(temp.color_background);
                        txt.setForeground(temp.color_foreground);
                        j++;
                        txt.setHorizontalAlignment(JTextField.CENTER);
                        txt_panel.add(txt);
                        colPanel.add(txt_panel);
                    }
                    colPanel.setLayout(new GridLayout(1, k, 1, 5));
                    rowPanel.add(colPanel);
                }
            }
            tempPanel.add(rowPanel);
            rowPanel.setBackground(Color.blue);
            rowPanel.setLayout(new GridLayout(1, exp(i), 5, 0));
        }
        tempPanel.setLayout(new GridLayout(row, 1, 0, 5));
        tempPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        tempPanel.setBackground(Color.blue);
        panel.add(tempPanel);
        panels.add(panel);
    }

    public int tree_height(int[] arr,int i)
    {
        if ((2*i+1)<arr.length) {
            return tree_height(arr,2*i+1)+1;
        }else{
            return 1;
        }
    }

    public int tree_gap(int i)
    {
        if (i>0) {
            return 2*tree_gap(i-1)+1;
        }
        return 0;
    }

    public void addTree(int[] arr,int high,Arr_Box... b)
    {
        JPanel panel = new JPanel();
        int h=tree_height(arr,0);
        int a = 1;
        int min = 1;
        int max = h;
        int p = 0;
        int num_l = num_l(arr);
        int q = 0;

        int[] pos = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            pos[i]=i+1;
        }
        Arr_Box temp = new Arr_Box(Color.white,Color.black, pos);

        for (int x = 1; x <= h; x++)
        {
            JPanel panel1 = new JPanel();
            for (int i = a; i < min+1 && p<high; i++)
            {
                temp = new Arr_Box(Color.white,Color.black, pos);

                for (Arr_Box box : b)
                {
                    for (int j = 0; j < box.pos.length; j++) {
                        if (box.pos[j]==p+1) {
                            temp=box;
                        }
                    }

                }

                JPanel panel2 = new JPanel();
                JTextField txt = new JTextField(""+arr[p],num_l+1);
                p++;
                a++;
                txt.setHorizontalAlignment(JTextField.CENTER);
                txt.setBorder(BorderFactory.createLineBorder(Color.black,6));        
                txt.setFont(new Font("Arial", Font.BOLD, 15));
                txt.setBackground(temp.color_background);
                txt.setForeground(temp.color_foreground);

                panel2.setLayout(new FlowLayout(FlowLayout.CENTER,1,0));
                panel2.add(txt);  
                panel1.add(panel2);
                // panel2.setBorder(BorderFactory.createLineBorder(Color.blue));
            }
            // panel1.setBorder(BorderFactory.createLineBorder(Color.red));
            if (!(p<high)) {
                for (int i = a; i < min+1; i++)
                {
                    JPanel panel2 = new JPanel();
                    JTextField txt = new JTextField(num_l+1);
                    p++;
                    txt.setHorizontalAlignment(JTextField.CENTER);
                    txt.setBackground(new Color(236, 239, 241));
                    txt.setBorder(BorderFactory.createLineBorder(new Color(236, 239, 241),6));
                    
                    panel2.setLayout(new FlowLayout(FlowLayout.CENTER,1,0));
                    panel2.add(txt);
                    panel1.add(panel2);
                }
            }

            panel1.setLayout(new GridLayout(1, x, 0, 0)); 
            panel.add(panel1); 


            JPanel panel_line = new JPanel();
            a=1;

            for (int i = a; i < min+1; i++)
            {
                JPanel r_panel1 = new JPanel();
                JPanel l_panel1 = new JPanel();
                JPanel r_panel2 = new JPanel();
                JPanel l_panel2 = new JPanel();
                LeftLine line1 = new LeftLine();
                RightLine line2 = new RightLine();
                l_panel2.setBorder(BorderFactory.createLineBorder(new Color(236, 239, 241)));
                panel_line.add(r_panel1);
                if (2*q+1<high) {
                    panel_line.add(line1);
                }else{
                    panel_line.add(r_panel2);
                }if (2*q+2<high) {
                    panel_line.add(line2);
                }else{
                    panel_line.add(l_panel2);
                }
                panel_line.add(l_panel1);
                q++;
                // line1.setBorder(BorderFactory.createLineBorder(Color.blue));
                // line2.setBorder(BorderFactory.createLineBorder(Color.red));
            }


            panel_line.setLayout(new GridLayout(1, 4*min, 0, 0)); 
            panel.add(panel_line); 

            a=1;
            min=min*2;
            max=max-1;
        }

        
        panel.setLayout(new GridLayout(2*h, 1, 0, 0));
        panels.add(panel);
    }

    public void addTree(int[] arr,int high)
    {
        Color[] colors = {Color.red,Color.blue,Color.green,Color.yellow,Color.pink};
        Arr_Box[] boxes = new Arr_Box[high+1];
        Arr_Box box;
        int n;
        for (int i = 0; i < high+1 ; i++) {
            n=i%colors.length;
            if (colors[n]==Color.red || colors[n]==Color.blue) {
                box = new Arr_Box(colors[n], Color.white, i+1);
            }else{
                box = new Arr_Box(colors[n], Color.black, i+1); 
            }
            boxes[i]=box;
        }
        addTree(arr, high, boxes);
    }

    public void addTree(int[] arr)
    {
        addTree(arr, arr.length);
    }

    public void warpup()
    {
        panels.setLayout(new BoxLayout(panels, BoxLayout.Y_AXIS));
        if (displays_l==0) {
            displays = new JPanel[1];
            displays[0] = new JPanel();
            displays[0].add(panels);
        }else{
            JPanel[] temp = new JPanel[displays_l+1];
            for (int i = 0; i < displays_l; i++) {
                temp[i]=new JPanel();
                temp[i].add(displays[i].getComponent(0));
            }
            temp[displays_l]=new JPanel();
            temp[displays_l].add(panels);
            displays = temp;
        }
        panels = new JPanel();
        panels_l=0;
        displays_l++;
    }
    
    public JPanel display()
    {
        JPanel panel = new JPanel();

        stop_round=displays_l+1;
        cur_round=0;

        Temp_timer.addActionListener(new ActionListener() {
            int x = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cur_round!=x) {
                    x=cur_round;
                }
                if (x==stop_round) {                    
                    panel.removeAll();
                    panel.add(displays[stop_round]);
                    panel.revalidate();
                    panel.repaint();
                }else{                    
                    panel.removeAll();
                    panel.add(displays[x]);
                    panel.revalidate();
                    panel.repaint();
                    x++;
                    cur_round++;
                    if (x==displays_l) {
                        x=0;
                        cur_round=0;
                    }
                }
            }
        }); 

        return panel;
    }
}

class Arr
{
    public int[] arr;
    public int[][] arr_2d;
    public int l;
    public int num_l;
    public GUI_Display_Arr gui;
    public int[] pivots;

    public Arr()
    {
        arr = null;
        l = 0;
        num_l = 0;
        gui = new GUI_Display_Arr();
    }

    public Arr(int[] nums)
    {
        gui = new GUI_Display_Arr();
        arr=nums;
        l=nums.length;
        num_l=0;
        for (int i = 0; i < l; i++) {
            if(num_l<String.valueOf(arr[i]).length()){
                num_l=String.valueOf(arr[i]).length();
            }
        }
    }

    public void update(int[] nums)
    {
        gui = new GUI_Display_Arr();
        arr=nums;
        l=nums.length;
        num_l=0;
        for (int i = 0; i < l; i++) {
            if(num_l<String.valueOf(arr[i]).length()){
                num_l=String.valueOf(arr[i]).length();
            }
        }
    }

    public JPanel display()
    {
        gui = new GUI_Display_Arr();
        gui.addText("Elements in Array");
        gui.addArr_color(arr);
        gui.warpup();
        return gui.display();

    }

    public void shuffle()
    {
        Random rand = new Random();

        for (int i = 0; i < l; i++) {
            int randomIndexToSwap = rand.nextInt(l);
            int temp = arr[randomIndexToSwap];
            arr[randomIndexToSwap] = arr[i];
            arr[i] = temp;
        }
    }

    public JPanel insertion() {
        gui = new GUI_Display_Arr();
        int[] pos1 = new int[l];
        Arr_Box box1;
        Arr_Box box2;
        gui.addArr_color(arr);
        gui.addText("INTIAL ORDER");
        gui.addText("STARTING INSERTION SORT......");
        gui.warpup();
        pos1[0]=1;
        for (int i = 1; i < l; ++i) 
        {
            int key = arr[i];
            int j = i - 1;
            box1 = new Arr_Box(Color.red, Color.white, pos1);
            box2 = new Arr_Box(Color.blue, Color.white, i+1);
            gui.addArr(arr, box1,box2);
            gui.addText("ELEMENTS IN SORTED ARRAY = ",Color.red);
            gui.addText("THE ELEMENT TO BE INSERTED INTO SORTED ARRAY = ",Color.blue);
            gui.addText("SHIFTING ELEMENTS GREATER THAN KEY TO ONE");
            gui.addText("POSITION AHEAD OF THIER CURRENT POSITION");
            gui.warpup();
            pos1[j+1]=j+2;
            box1 = new Arr_Box(Color.red, Color.white, pos1);
            while (j >= 0 && arr[j] > key) {
                box2 = new Arr_Box(Color.blue, Color.white, j+2);
                gui.addArr(arr, box1,box2);
                gui.addText("ELEMENTS IN SORTED ARRAY = ",Color.red);
                gui.addText("THE ELEMENT TO BE INSERTED INTO SORTED ARRAY = ",Color.blue);
                gui.addText("SHIFTING ELEMENTS GREATER THAN KEY TO ONE");
                gui.addText("POSITION AHEAD OF THIER CURRENT POSITION");
                gui.warpup();
                arr[j + 1] = arr[j];
                j = j - 1;
                arr[j + 1] = key;
            }
            box2 = new Arr_Box(Color.blue,Color.white, j+2);
            gui.addArr(arr, box1,box2);
            gui.addText("AFTER SHIFTING");
            gui.warpup();
        }
        gui.addArr_color(arr);
        gui.addText("FINAL ORDER");
        gui.warpup();
        return gui.display();
    }

    public void merge_arr(int row,int low,int mid,int high){    
        int  i=low;
        int j=mid+1;
        int k=low;
        boolean e = false; 
        int[] b_arr = new int[l];
        Arr b = new Arr(b_arr);
        b.num_l=num_l;
        int[] pos1 = new int[mid+1];
        int[] pos2 =  new int[high-mid+1];
        int[] pos3 = new int[high+1-low];
        for (int x = low; x < mid+1; x++) { 
            pos1[x-low]=x+1;
        }
        for (int x = mid+1; x < high+1; x++) {
            pos2[x-(mid+1)]=x+1;
        }
        for (int x = 0; x < pos3.length; x++) {
            pos3[x]=x+1+low;
        }
        Arr_Box box1 = new Arr_Box(row+1,Color.red, Color.white, pos1);
        Arr_Box box2 = new Arr_Box(row+1,Color.green, Color.black, pos2);
        Arr_Box box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
        Arr_Box box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
        Arr_Box box5 = new Arr_Box(row+1,Color.blue,Color.white, k+1);
        Arr_Box box6 = new Arr_Box(row,Color.yellow, Color.black, pos3);

        if (row==gui.depth(arr.length)) {
            box1 = new Arr_Box(row,Color.red, Color.white, pos1);
            box2 = new Arr_Box(row,Color.green, Color.black, pos2);
            box6 = new Arr_Box(row+1,Color.yellow, Color.black, pos3);
            e = true;
            row--;
        }

        gui.addArr_2d(arr_2d, box2,box1,box6);
        gui.addText("INSERTING THE ELEMENTS INTO THE");
        gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
        gui.addText("FIRST ARRAY = ", Color.red); 
        gui.addText("SECOND ARRAY = ", Color.green);
        gui.addText("SMALLEST ELEMENT = ", Color.blue);
        gui.addText("TRANSFER ARRAY = ", Color.yellow);
        gui.warpup();
        

        while(i<=mid&&j<=high){            
            box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
            box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
            box5 = new Arr_Box(row,Color.blue,Color.white, k+1);
            if(arr_2d[row][i]<arr_2d[row][j]){   
                if (e) {                                
                    box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
                    box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
                    box5 = new Arr_Box(row+1,Color.blue,Color.white, k+1); 
                    
                    gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();
                }else{

                    gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();

                    arr_2d[row-1][k]=arr_2d[row][i];

                    gui.addArr_2d(arr_2d,box6, box2,box1,box5);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();


                } 
                i++;
                k++;
            }else{
                if (e) {                                
                    box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
                    box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
                    box5 = new Arr_Box(row+1,Color.blue,Color.white, k+1); 
                    
                    gui.addArr_2d(arr_2d,box6, box2,box1,box4);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();

                    int temp = arr_2d[row][i];
                    arr_2d[row][i] = arr_2d[row][j];
                    arr_2d[row][j] =temp;

                    gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();
                }else{  

                    gui.addArr_2d(arr_2d,box6, box2,box1,box4);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();

                    arr_2d[row-1][k]=arr_2d[row][j];

                    gui.addArr_2d(arr_2d,box6, box2,box1,box5);
                    gui.addText("INSERTING THE ELEMENTS INTO THE");
                    gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                    gui.addText("FIRST ARRAY = ", Color.red); 
                    gui.addText("SECOND ARRAY = ", Color.green);
                    gui.addText("SMALLEST ELEMENT = ", Color.blue);
                    gui.addText("TRANSFER ARRAY = ", Color.yellow);
                    gui.warpup();

                } 
                j++;
                k++;
            }
        }
        while(i<=mid){   
            if (e) {                                
                box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
                box5 = new Arr_Box(row+1,Color.blue,Color.white, k+1); 
                
                gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();
            }else{
                box3 = new Arr_Box(row+1,Color.blue,Color.white, i+1);
                box5 = new Arr_Box(row,Color.blue,Color.white, k+1);
                
                gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();

                arr_2d[row-1][k]=arr_2d[row][i];

                gui.addArr_2d(arr_2d,box6, box2,box1,box5);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();


            } 
            i++;
            k++;
        }
        while(j<=high){
            if (e) {    
                box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
                box5 = new Arr_Box(row+1,Color.blue,Color.white, k+1); 
                
                gui.addArr_2d(arr_2d,box6, box2,box1,box4);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();

                int temp = arr_2d[row][i];
                arr_2d[row][i] = arr_2d[row][j];
                arr_2d[row][j] =temp;

                gui.addArr_2d(arr_2d,box6, box2,box1,box3);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();
            }else{    
                box4 = new Arr_Box(row+1,Color.blue,Color.white, j+1);
                box5 = new Arr_Box(row,Color.blue,Color.white, k+1); 

                gui.addArr_2d(arr_2d,box6, box2,box1,box4);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();

                arr_2d[row-1][k]=arr_2d[row][j];

                gui.addArr_2d(arr_2d,box6, box2,box1,box5);
                gui.addText("INSERTING THE ELEMENTS INTO THE");
                gui.addText("TRANSFER ARRAY IN ORDER FROM MAIN ARRAY");
                gui.addText("FIRST ARRAY = ", Color.red); 
                gui.addText("SECOND ARRAY = ", Color.green);
                gui.addText("SMALLEST ELEMENT = ", Color.blue);
                gui.addText("TRANSFER ARRAY = ", Color.yellow);
                gui.warpup();

            } 
            j++;
            k++;
        }
    }

    public void cut_arr(int row,int i,int j){
        if(i<j){
            int mid=(i+j)/2;
            cut_arr(row+1,i,mid);
            cut_arr(row+1,mid+1,j);
            merge_arr(row,i,mid,j);
        }
    }

    public JPanel merge()
    {
        gui = new GUI_Display_Arr();
        arr_2d = gui.make_2d_arr(gui.depth(arr.length)-1, arr);
        gui.addArr_color(arr);
        gui.addText("INTIAL ORDER");
        gui.addText("STARTING MERGE SORT.......");
        gui.warpup();

        cut_arr(1,0, l-1);

        gui.addArr_color(arr_2d[0]);
        gui.addArr(arr);
        gui.addText("COPYING THE ELEMENTS TO MAIN ARRAY");
        gui.warpup();

        arr=arr_2d[0];

        gui.addArr(arr_2d[0]);
        gui.addArr_color(arr);
        gui.addText("AFTER COPYING");
        gui.warpup();

        gui.addArr_color(arr);
        gui.addText("FINAL ORDER");
        gui.warpup();

        return gui.display();
    }

    public JPanel quick()
    {
        gui = new GUI_Display_Arr();
        pivots = new int[arr.length];
        gui.addArr_color(arr);
        gui.addText("INTIAL ORDER");
        gui.addText("STARTING QUICK SORT.......");
        gui.warpup();

        quickSort(0, l-1);

        gui.addArr_color(arr);
        gui.addText("FINAL ORDER");
        gui.warpup();

        return gui.display();

    }

    public void quickSort(int low, int high) {
        if (low < high) {
            // Partition the array
            int pivotIndex = partition(low, high);
            pivots[pivotIndex]=pivotIndex+1;

            // Recursively sort the sub-arrays
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        int[] pos1 = new int[l];
        int[] pos2 = new int[l];
        Arr_Box box1 = new Arr_Box(Color.blue,Color.white, high+1);
        Arr_Box box2 = new Arr_Box(Color.yellow,Color.black, low+1);
        Arr_Box box3 = new Arr_Box(Color.red,Color.white, pos1);
        Arr_Box box4 = new Arr_Box(Color.green,Color.white, pos2);
        Arr_Box box5 = new Arr_Box(Color.magenta, Color.white, pivots);
        int pivot = arr[high];
        int i = low - 1;
        gui.addArr(arr,box4,box3,box2,box1,box5);
        gui.addText("SWAPING THE ELEMENTS BY COMPARING TO PIVORT");
        gui.addText("PIVOT = ",Color.blue);
        gui.addText("POINTER = ",Color.yellow);
        gui.addText("SMALLER THAN PIVOT = ",Color.red);
        gui.addText("GREATER THAN PIVOT = ",Color.green);
        gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
        gui.warpup();

        for (int j = low; j < high; j++) {
            box2 = new Arr_Box(Color.yellow,Color.black, j+1);
            box3 = new Arr_Box(Color.red,Color.white, pos1);
            box4 = new Arr_Box(Color.green,Color.white, pos2);
            if (arr[j] < pivot) {
                i++;

                gui.addArr(arr,box4,box3,box2,box1,box5);
                gui.addText("SWAPING THE ELEMENTS BY COMPARING TO PIVORT");
                gui.addText("PIVOT = ",Color.blue);
                gui.addText("POINTER = ",Color.yellow);
                gui.addText("SMALLER THAN PIVOT = ",Color.red);
                gui.addText("GREATER THAN PIVOT = ",Color.green);
                gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
                gui.warpup();

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;                
                pos1[i-low]=i+1;
                pos2[j-low]=j+1;
            }
            else{

                gui.addArr(arr,box4,box3,box2,box1,box5);
                gui.addText("SWAPING THE ELEMENTS BY COMPARING TO PIVORT");
                gui.addText("PIVOT = ",Color.blue);
                gui.addText("POINTER = ",Color.yellow);
                gui.addText("SMALLER THAN PIVOT = ",Color.red);
                gui.addText("GREATER THAN PIVOT = ",Color.green);
                gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
                gui.warpup();

                pos2[j-low]=j+1;
            }
        }

        gui.addArr(arr,box4,box3,box2,box1,box5);
        gui.addText("SWAPING THE ELEMENTS BY COMPARING TO PIVORT");
        gui.addText("PIVOT = ",Color.blue);
        gui.addText("POINTER = ",Color.yellow);
        gui.addText("SMALLER THAN PIVOT = ",Color.red);
        gui.addText("GREATER THAN PIVOT = ",Color.green);
        gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
        gui.warpup();

        // Swap arr[i+1] and arr[high] (or pivot)
        gui.addArr(arr,box4,box3,box1,box5);
        gui.addText("SWAPING THE PIVORT AFTER ELEMENTS SMALLER THAN PRIVORT");
        gui.addText("PIVOT = ",Color.blue);
        gui.addText("POINTER = ",Color.yellow);
        gui.addText("SMALLER THAN PIVOT = ",Color.red);
        gui.addText("GREATER THAN PIVOT = ",Color.green);
        gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
        gui.warpup();

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        pos2[0]=0;
        pos2[high]=high+1;
        
        box4 = new Arr_Box(Color.green,Color.white, pos2);
        box1 = new Arr_Box(Color.blue,Color.white, i+2);

        gui.addArr(arr,box4,box3,box1,box5);
        gui.addText("SWAPING THE PIVORT AFTER ELEMENTS SMALLER THAN PRIVORT");
        gui.addText("PIVOT = ",Color.blue);
        gui.addText("POINTER = ",Color.yellow);
        gui.addText("SMALLER THAN PIVOT = ",Color.red);
        gui.addText("GREATER THAN PIVOT = ",Color.green);
        gui.addText("PREVIOUS PIVOTS = ",Color.magenta);
        gui.warpup();

        return i + 1;

    }

    public JPanel heap()
    {
        gui = new GUI_Display_Arr();
        int N = l;
        String str;
        int n;
        int[] pos1 = new int[l];
        Arr_Box box;
        Arr_Box[] boxes = new Arr_Box[l];
        Arr_Box box1 = new Arr_Box(Color.blue,Color.white, 1);
        Arr_Box box2 = new Arr_Box(Color.magenta,Color.white, l);

        gui.addArr_color(arr);
        gui.addText("INTIAL ORDER");
        gui.addText("STARTING HEAP SORT.......");
        gui.warpup();

        gui.addText("COVERTING THE UNSORTED ARRAY INTO TREE");
        gui.addArr_color(arr);
        gui.addText("COVERTING...........");
        gui.addTree(arr);
        gui.warpup();
        
        for (int i = N / 2 - 1; i >= 0; i--)heapify( N, i);

        for (int i = N - 1; i > 0; i--) {
            box1 = new Arr_Box(Color.blue,Color.white, 1);
            box2 = new Arr_Box(Color.magenta, Color.white, i+1);
            
            gui.addTree(arr,i+1);
            gui.addText("THE ELEMENTS IN THE HEAP");
            gui.warpup();

            gui.addTree(arr,i+1);
            gui.addText("COVERTING THE HEAP INTO ARRAY");
            gui.addArr_color(0, i+1, arr);
            gui.warpup();

            gui.addArr(arr, box1,box2);
            gui.addText("SWAPPING THE FIRST AND LAST ELEMENTS");
            gui.warpup();
            
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            box1 = new Arr_Box(Color.blue,Color.white, i+1);
            box2 = new Arr_Box(Color.magenta, Color.white, 1);

            gui.addArr(arr, box1,box2);
            gui.addText("AFTER SWAPPING");
            gui.warpup();

            if (i==1) {
                break;
            }

            gui.addText("COVERTING THE UNSORTED ARRAY INTO TREE");
            gui.addText("SORTED ARRAY = ", Color.white);
            gui.addArr_color(1, i, arr);
            gui.addText("COVERTING...........");
            gui.addTree(arr, i);
            gui.warpup();

            heapify(i, 0);
        }
        
        gui.addArr_color(arr);
        gui.addText("INTIAL ORDER");
        gui.addText("STARTING HEAP SORT.......");
        gui.warpup();

        return gui.display();
    }

    public void  heapify( int N, int i)
    {
        int largest = i; 
        int l = 2 * i + 1; 
        int r = 2 * i + 2;         
        Arr_Box box1 = new Arr_Box(Color.blue,Color.white, i+1);
        Arr_Box box2 = new Arr_Box(Color.magenta,Color.white, l+1,r+1);

        gui.addTree(arr, N,box1,box2);
        gui.addText("COVERTING THE TREE INTO HEAP");
        gui.addText("PARENT = ",Color.blue);
        gui.addText("CHILD = ",Color.magenta);
        gui.addText("SMALLER ELEMENT = ",Color.red);
        gui.addText("LARGER ELEMENT = ",Color.green);
        gui.addText("OTHER ELEMENTS = ",Color.white);
        gui.warpup();

        if (l < N && arr[l] > arr[largest])
            largest = l;
        if (r < N && arr[r] > arr[largest])
            largest = r;
        if (largest != i) {
            box1 = new Arr_Box(Color.red, Color.white, largest+1);
            box2 = new Arr_Box(Color.green,Color.black,i+1); 
            
            gui.addTree(arr, N,box1,box2);
            gui.addText("COVERTING THE TREE INTO HEAP");
            gui.addText("PARENT = ",Color.blue);
            gui.addText("CHILD = ",Color.magenta);
            gui.addText("SMALLER ELEMENT = ",Color.red);
            gui.addText("LARGER ELEMENT = ",Color.green);
            gui.addText("OTHER ELEMENTS = ",Color.white);
            gui.warpup();
            
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
                   
            box1 = new Arr_Box(Color.red, Color.white, i+1);
            box2 = new Arr_Box(Color.green,Color.black,largest+1); 

            gui.addTree(arr, N,box1,box2);
            gui.addText("COVERTING THE TREE INTO HEAP");
            gui.addText("PARENT = ",Color.blue);
            gui.addText("CHILD = ",Color.magenta);
            gui.addText("SMALLER ELEMENT = ",Color.red);
            gui.addText("LARGER ELEMENT = ",Color.green);
            gui.addText("OTHER ELEMENTS = ",Color.white);
            gui.warpup();

            heapify(N, largest);
        }
    }

    public JPanel test()
    {
        GUI_Display_Arr gui = new GUI_Display_Arr();
        gui.addTree(arr);
        gui.addArr_color(arr);
        gui.addArr_color(arr);
        gui.addArr_color(arr);
        gui.addArr_color(arr);
        gui.warpup();
        return gui.display();
    }
}


class Sort6 {  

    public static void swap_panel(JPanel parent,JPanel panel)
    {
        parent.removeAll();
        parent.add(panel);
        parent.revalidate();
        parent.repaint();

    }
    public static void main(String args[]) { 

        Arr num = new Arr();
        Arr num1 = new Arr();

        //Initializing frame
        JFrame frame = new JFrame("Vistiualtion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Components

        JPanel panel = new JPanel();
        JTextField input = new JTextField(30);
        JButton update_btn = new JButton("Update");
        JButton shuffle_btn = new JButton("Shuffle");
        JPanel output1 = new JPanel();
        JButton insertion_btn = new JButton("Insertion");
        JButton merge_btn = new JButton("Merge");
        JButton quick_btn = new JButton("Quick");
        JButton heap_btn = new JButton("Heap");
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton previous = new JButton("Previous");
        JButton next = new JButton("Next");
        JPanel output2 = new JPanel();
        JPanel main_panel = new JPanel();

        //Button functions

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                num.gui.stop_round=num.gui.displays_l+1;
                // num.gui.Temp_timer.start();

            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                num.gui.stop_round=num.gui.cur_round;
                num.gui.cur_round--;

            }
        });

        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (num.gui.stop_round==0) {
                    num.gui.stop_round=num.gui.displays_l-1;
                    num.gui.cur_round=num.gui.displays_l-1;
                }else{
                    num.gui.stop_round--;
                    num.gui.cur_round--;
                }

            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (num.gui.stop_round==num.gui.displays_l-1) {
                    num.gui.stop_round=0;
                    num.gui.cur_round=0;
                }else{
                    num.gui.stop_round++;
                    num.gui.cur_round++;
                }

            }
        });

        update_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String inputText = input.getText();
                String[] inputArray = inputText.split("\\s+");
                int[] intArray = new int[inputArray.length];
                for (int i = 0; i < inputArray.length; i++) {
                    intArray[i] = Integer.parseInt(inputArray[i]);
                }
                
                num.update(intArray);
                num1.update(intArray);

                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();

            }
        });

        shuffle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String inputText = input.getText();
                String[] inputArray = inputText.split("\\s+");
                int[] intArray = new int[inputArray.length];
                for (int i = 0; i < inputArray.length; i++) {
                    intArray[i] = Integer.parseInt(inputArray[i]);
                }
                
                num.update(intArray);
                num.shuffle();
                num1.update(intArray);
                num1.shuffle();

                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();

            }
        });

        insertion_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                swap_panel(output2, num.insertion());
                num1.insertion();
                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();
                num.gui.Temp_timer.start();

            }
        });

        merge_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                swap_panel(output2, num.merge());
                num1.merge();
                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();
                num.gui.Temp_timer.start();

            }
        });

        quick_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                swap_panel(output2, num.quick());
                num1.quick();
                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();
                num.gui.Temp_timer.start();

            }
        });
        
        heap_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                swap_panel(output2, num.heap());
                num1.heap();
                swap_panel(output1, num1.display());
                num1.gui.Temp_timer.start();
                num.gui.Temp_timer.start();               

            }
        });

        //adding different panels into main panel
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        panel1.add(input);
        panel2.add(update_btn);
        panel2.add(shuffle_btn);
        panel3.add(output1);
        panel4.add(insertion_btn);
        panel4.add(merge_btn);
        panel4.add(quick_btn);
        panel4.add(heap_btn);
        panel5.add(output2);
        panel6.add(start);
        panel6.add(stop);
        panel6.add(previous);
        panel6.add(next);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        panel.add(panel6);

        //Components layout
        input.setFont(new Font("Arial", Font.PLAIN, 15));
        input.setPreferredSize(new Dimension(0, 30));
        output1.setPreferredSize(new Dimension(1200, 100));
        output1.setBorder(BorderFactory.createLineBorder(Color.black));
        output2.setPreferredSize(new Dimension(1200, 500));
        output2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 100,10 ));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 100,10));
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 100,10));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        // main_panel.setBorder(BorderFactory.createLineBorder(Color.red,15));
        main_panel.setBorder(BorderFactory.createLineBorder(new Color(236, 239, 241),15));

     
        
        //finishing the frame
        main_panel.add(panel);
        frame.add(main_panel);
        frame.setSize(1300, 850);  
        frame.setVisible(true);
        
        
      }  
} 