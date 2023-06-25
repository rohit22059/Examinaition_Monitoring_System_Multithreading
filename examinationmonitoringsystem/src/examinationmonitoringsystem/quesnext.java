/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examinationmonitoringsystem;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Rohit Kesarwani
 */

public class quesnext extends javax.swing.JFrame {

    /**
     * Creates new form quesnext
     */
     JTextField jt[][];
int noq;
String course,session,sem,sub,minute;
    public quesnext(int noq,String course,String session,String sem,String sub,String minute) {
        
          //   System.out.println(noq +" "+  noo);
          this.noq=noq;
          
          this.course=course;
          this.session=session;
          this.sem=sem;
          this.sub=sub;
          this.minute=minute;
          Statement s=dh.connect();
          try
          {
              ResultSet r=s.executeQuery("select count(*) from qdetails where course='"+course+"' and syear='"+session+"' and sem='"+sem+"' and subject='"+sub+"'");
              r.next();
              if(r.getInt(1)>0)
              {
                  JOptionPane.showMessageDialog(this,"Question Already inserted","Error", JOptionPane.ERROR_MESSAGE);
                  this.setVisible(false);
                  new QuesDetails().setVisible(true);
              }
              else
              { 
                    initComponents();
                     addcomponents();
    
               }
            
          }catch(Exception e){
              System.out.println(e.getMessage());}
   
    }
    private String passcode()
    {
        String p="abcdefghijklmnopqrstuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ";
        String l="";
        for(int i=0;i<6;i++)
        {
            int k=(int)(Math.random()*100)/10;
            l=l+p.charAt(k);
        }
        return l;
                
    }
    private boolean savequestion(String pass)
    {
       
        Statement s=dh.connect();
        int i=0,count=0;;   
        while(i<noq)
           {
            try
            {
               if(!jt[i][0].getText().equals(""))
               {
                s.executeUpdate("insert into questions"+pass+" values("+i+",'"+jt[i][0].getText().trim()+"','"+jt[i][1].getText().trim()+"','"+jt[i][2].getText().trim()+"','"+jt[i][3].getText().trim()+"','"+jt[i][4].getText().trim()+"','"+jt[i][5].getText().trim().toUpperCase()+"')");
                   
                  dh.commit();
             count++;
               }  
               i++;  
                //   JOptionPane.showMessageDialog(this,"inserted question successfully","success",JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){System.out.println("problem is here "+e.getMessage()); return false;}
         }
        try
        {
            s.executeUpdate("update qdetails set noq="+count+" where password='"+pass+"'");
        }catch(Exception e){}
           
              return true;
    }
    private boolean savequestiondetail()
    {
    Statement s=dh.connect();
    try
    {
              boolean t;
             String pass;
            do
            {
                pass=passcode();
                t=true;
           // ResultSet r=s.executeQuery("select count(*) from qdetails where password='"+pass+"'");
            //r.next();
            //if(r.getInt(1)>0)
            //{
              //  t=false;
             
           // }
            }while(t==false);
            System.out.println("start");
                  s.executeUpdate("insert into qdetails values('"+course+"','"+session+"','"+sem+"','"+minute+"','"+sub+"','"+noq+"','"+pass+"')");
                   dh.commit();
                   System.out.println("end");
                   String tab="questions"+pass;
                   String tab2="studresult"+pass;
                  s.execute("create table "+tab+"(qid integer(5),ques varchar(300),A varchar(200),B varchar(200),C varchar(200),D varchar(200),rans varchar(5))");
                  s.execute("create table "+tab2+"(uroll varchar(20),atques integer(5),dot date,per varchar(10),tques integer(5),trans integer(5))");
                  savequestion(pass);
                  JOptionPane.showMessageDialog(this,"kindly note this passcode\n"+pass,"Passcode",JOptionPane.INFORMATION_MESSAGE);    
    }catch(Exception e){
        System.out.println("savesstuddetail"+e.getMessage()); return false;}
                   return true;
    }
    
    private boolean checkblankfield()
    {
        for(int i=0;i<noq;i++)
        {
        if(!jt[i][0].getText().equals(""))
        {
           for(int k=0;k<=5;k++)
           {
               if(jt[i][k].getText().equals(""))
               {
                   return false;
                   
               }
           }
        }
        }
        return true;
    }
private void addcomponents()
{
    
 
    /*scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scr.setBounds(50,30,300,50);
    qpanel.add(scr);
    */
       jt =new JTextField[noq][4+2];
    qpanel.setLayout(new GridLayout(noq+1, 4+3));
   /// JTextArea j[]=new JTextArea[noq];
    JLabel jl[]=new JLabel[noq];
    JLabel nojl[]=new JLabel[4+1];
    qpanel.add(new JLabel(""));
    qpanel.add(new JLabel(""));
    int h;
    
        nojl[0]=new JLabel("   A");
        qpanel.add(nojl[0]);
        nojl[1]=new JLabel("   B");
        qpanel.add(nojl[1]);
    nojl[2]=new JLabel("    C");
        qpanel.add(nojl[2]);
    nojl[3]=new JLabel("   D");
        qpanel.add(nojl[3]);
    
        
    nojl[4]=new JLabel("Answer(A-D)");
    qpanel.add(nojl[4]);
   for(int i=0;i<noq;i++)
    {
        jl[i]=new JLabel("Q"+(i+1));
        jl[i].setSize(i, i);
         qpanel.add(jl[i]);
         for(int k=0;k<=4+1;k++)
        {
            
            jt[i][k]=new JTextField();
       //     jt[i][k].setSize(1,40);
            
            qpanel.add(jt[i][k]);
        }
    }
   // JScrollPane scr=new JScrollPane(qpanel);
       /* */
    
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        qpanel = new javax.swing.JPanel();
        qpanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        qpanel.setPreferredSize(new java.awt.Dimension(500, 422));

        javax.swing.GroupLayout qpanelLayout = new javax.swing.GroupLayout(qpanel);
        qpanel.setLayout(qpanelLayout);
        qpanelLayout.setHorizontalGroup(
            qpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );
        qpanelLayout.setVerticalGroup(
            qpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        qpanel1.setPreferredSize(new java.awt.Dimension(500, 422));

        javax.swing.GroupLayout qpanel1Layout = new javax.swing.GroupLayout(qpanel1);
        qpanel1.setLayout(qpanel1Layout);
        qpanel1Layout.setHorizontalGroup(
            qpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );
        qpanel1Layout.setVerticalGroup(
            qpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        jButton1.setText("Submit Question");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(qpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(qpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(qpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     Statement s=dh.connect();   
     if(checkblankfield())
     {
     try
        {
          if(savequestiondetail())
          {
              JOptionPane.showMessageDialog(this, "Successfully saved","Success",JOptionPane.INFORMATION_MESSAGE);
          }
        
        
        }catch(Exception e){System.out.println(e.getMessage());}    
        
        
     }  
        
    }//GEN-LAST:event_jButton1ActionPerformed
   
            
     
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel qpanel;
    private javax.swing.JPanel qpanel1;
    // End of variables declaration//GEN-END:variables
DatabaseHelper dh=new DatabaseHelper();

}


