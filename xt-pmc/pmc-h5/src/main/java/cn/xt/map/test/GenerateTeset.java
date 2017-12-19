package cn.xt.map.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class GenerateTeset {
    public static void main(String[] args) throws IOException {
        InputStream in = GenerateTeset.class.getClassLoader().getResourceAsStream("map.svg.png");
        BufferedImage image = ImageIO.read(in);

        int originX = image.getWidth() / 2 - 40;
        int originY = image.getHeight() / 2;

        graphics(image, "原点", originX, originY);

        int x = getX(116.4009766800, originX);
        int y = getY(39.9110666857, originY);
        graphics(image, "北京", x, y);

        x = getX(121.4692482700, originX);
        y = getY(31.2363429624, originY);
        graphics(image, "上海", x, y);

        x = getX(104.0712219292, originX);
        y = getY(30.5763307666, originY);
        graphics(image, "成都", x, y);

        x = getX(110.2064297546, originX);
        y = getY(20.0499987102, originY);
        graphics(image, "海口", x, y);

        x = getX(121.5200760000, originX );
        y = getY(25.0307240000, originY);
        graphics(image, "台湾", x, y);

        x = getX(-76.96666666666667, originX);
        y = getY(38.88333333333333, originY);
        graphics(image, "华盛顿", x, y);

        /*x = getX(131.88333333333333, originX,43.15);
        y = getY(43.15, originY);
        graphics(image, "符拉迪沃斯托克", x, y);*/
        in.close();
    }

    public static int getX(double n, int originX,double...n2) {
        int lat = originX + new BigDecimal(n* 4.53).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        lat = lat + 4;
        return lat;
    }

    public static int getY(double n, int originY) {
        int lng = new BigDecimal(n).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        lng = originY - new BigDecimal(lng * 5.8).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
        return lng;
    }

    public static void graphics(BufferedImage screenImg, String label, int x, int y) throws IOException {
        //标注找到的图的位置
        Graphics g = screenImg.getGraphics();
        Graphics2D g2=(Graphics2D)g;
        // 消除锯齿
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        qualityHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHints(qualityHints);

        g2.setColor(Color.RED);
        /*double lat = 116.4136103013;
        double lng = 39.9110666857;

        x = new BigDecimal(lat).intValue();
        y = new BigDecimal(lng).intValue();*/
        g2.fillOval(x, y, 4, 4);
        g2.setFont(new Font(null, Font.BOLD, 12));
        g2.drawString(label, x + 2, y - 2);
        FileOutputStream out = new FileOutputStream("d:/generateMap.png");
        ImageIO.write(screenImg, "png", out);
        out.close();
    }
}
