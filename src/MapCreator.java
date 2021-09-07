import java.awt.*;

public class MapCreator {
    public int map[][];

    public int width;   //brick width
    public int height;  //brick height

    public MapCreator(int row, int col){
        map = new int[row][col];
        for(int i = 0 ; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
            }
        }
        width = 600/ col;
        height = 200/ row;
    }

    public void draw(Graphics2D g){
        for(int i = 0 ; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j * width + 50, i * height + 50, width, height);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * width + 50 /* distance from vertical walls */, i * height + 50 /* distance from vertical walls */, width, height);
                }
            }
        }
    }

    public void brickValue(int value, int row, int col){
        map[row][col] = value;
    }
}
