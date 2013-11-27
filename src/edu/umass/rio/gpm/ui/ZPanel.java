/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZPanel.java
 *
 * Created on 2013-11-24, 16:05:48
 */
package edu.umass.rio.gpm.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jujuhoo
 */
public class ZPanel extends javax.swing.JPanel {
private static final long serialVersionUID = 1L;  
    private Image image;  
    private int imgWidth;  
    private int imgHeight;  
    private Image image_output;
    public int getImgWidth() {  
        return imgWidth;  
    }  
  
    public void setImgWidth(int imgWidth) {  
        this.imgWidth = imgWidth;  
    }  
  
    public int getImgHeight() {  
        return imgHeight;  
    }  
  
    public void setImgHeight(int imgHeight) {  
        this.imgHeight = imgHeight;  
    }  
  
    public void setImagePath(String imgPath) {  
        // 该方法不推荐使用，该方法是懒加载，图像并不加载到内存，当拿图像的宽和高时会返回-1；  
        // image = Toolkit.getDefaultToolkit().getImage(imgPath);  
        try {  
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。  
            image = ImageIO.read(new FileInputStream(imgPath));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        setImgWidth(image.getWidth(this));  
        setImgHeight(image.getHeight(this)); 
        if(this.getAutoscrolls()){
            int width = this.imgWidth;
            int height = this.imgHeight;//(int)((double)width/(double)this.imgWidth *(double)this.imgHeight);
            image_output =  image;

           Dimension d = new Dimension();
            d.setSize(width, height);
            this.setPreferredSize(d);
        }else{
            image_output = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        }
//        System.out.println(this.getHeight());
//        this.setSize(image.getWidth(this), image.getHeight(this));
//        System.out.println(this.getHeight());
    }  
  
    @Override  
    public void paintComponent(Graphics g1) {  
        int x = 0;  
        int y = 0;  
        Graphics g = (Graphics) g1;  
        if (null == image) {  
            return;  
        } 
        g.drawImage(image_output, x, y, image_output.getWidth(this), image_output.getHeight(this),  
                this);  
        g = null;  
    }  
    /** Creates new form ZPanel */
    public ZPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        if(image != null){
         image_output = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        }
        
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
