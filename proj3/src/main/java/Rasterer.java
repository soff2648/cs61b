import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {

//        System.out.println(params);
        Map<String, Object> results = testRaster(params);
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        return results;
    }



    public Map<String, Object> testRaster(Map<String, Double> params) {
        /*
        test 12
         */
//        Map<String, Double> params = new HashMap<>();
//        params.put("lrlon", -122.2104604264636);
//        params.put("ullon", -122.30410170759153);
//        params.put("w", 1091.0);
//        params.put("h", 566.0);
//        params.put("ullat", 37.870213571328854);
//        params.put("lrlat", 37.8318576119893);

        /*
        test
         */
//       Map<String, Double> params = new HashMap<>();
//        params.put("lrlon", -122.24053369025242);
//        params.put("ullon", -122.24163047377972);
//        params.put("w", 892.0);
//        params.put("h", 875.0);
//        params.put("ullat", 37.87655856892288);
//        params.put("lrlat", 37.87548268822065);
        int depth = checkDepth(params);

        int xL = xLeft(params, depth);

        int xR = xRight(params, depth);

        int yU = yUp(params, depth);

        int yL = yLower(params, depth);

        String[][] renderGrid = getGrid(xL, xR, yU, yL, depth);
        double rasterULLON = getRasterULLON(xL, depth);

        double rasterLRLON = getRasterLRLON(xR, depth);

        double rasterULLAT = getRasterULLAT(yU, depth);

        double rasterLRLAT = getRasterLRLAT(yL, depth);

        Map<String, Object> results = new HashMap<>();
//        System.out.println(depth);
//        System.out.println(xL);
//        System.out.println(xR);
//        System.out.println(yU);
//        System.out.println(yL);
//        System.out.println(rasterULLON);
//        System.out.println(rasterLRLON);
//        System.out.println(rasterLRLAT);
//        System.out.println(rasterULLAT);
        results.put("depth", depth);
        results.put("render_grid", renderGrid);
        results.put("query_success", checkSuccessQuery(params));
        results.put("raster_ul_lon", rasterULLON);
        results.put("raster_lr_lon", rasterLRLON);
        results.put("raster_lr_lat", rasterLRLAT);
        results.put("raster_ul_lat", rasterULLAT);

        return results;
    }

    public static void main(String[] args) {
                /*
        test 12
         */
        Map<String, Double> params12 = new HashMap<>();
        params12.put("lrlon", -122.2104604264636);
        params12.put("ullon", -122.30410170759153);
        params12.put("w", 1091.0);
        params12.put("h", 566.0);
        params12.put("ullat", 37.870213571328854);
        params12.put("lrlat", 37.8318576119893);

        Rasterer rasterer = new Rasterer();
        rasterer.testRaster(params12);
    }

    private boolean checkSuccessQuery(Map<String, Double> params) {
        double paramsULLON = params.get("ullon");
        if (paramsULLON < MapServer.ROOT_ULLON) {
            //System.out.println("ullon");
            return false;
        }
        double paramsLRLON = params.get("lrlon");
        if (paramsLRLON > MapServer.ROOT_LRLON) {
            //System.out.println("lrlon");
            return false;
        }

        double paramsULLAT = params.get("ullat");
        if (paramsULLAT > MapServer.ROOT_ULLAT) {
            //System.out.println("ullat");
            return false;
        }

        double paramsLRLAT = params.get("lrlat");
        if (paramsLRLAT < MapServer.ROOT_LRLAT) {
            //System.out.println("lrlat");
            return false;
        }

        return true;

    }

    private int checkDepth(Map<String, Double> params) {
        double paramsLRLON = params.get("lrlon");
        double paramsULLON = params.get("ullon");
        double paramsW = params.get("w");

        double paraLonDPP = (paramsLRLON - paramsULLON) / paramsW;
        //System.out.println(paraLonDPP);

        double basicLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        //System.out.println(basicLonDPP);

        int depth = 0;
        for ( ; depth <= 7; depth++) {
            double newLonDPP = basicLonDPP / Math.pow(2, depth);
            //System.out.println(newLonDPP);
            if (paraLonDPP >= newLonDPP) {
                //System.out.println(depth);
                //System.out.println(depth);
                break;
            }
        }
        return Math.min(depth, 7);
    }

    private int xLeft(Map<String, Double> params, int depth) {
        double paramsULLON = Math.max(params.get("ullon"), MapServer.ROOT_ULLON);

        double depthSizeLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        int result = (int) ((paramsULLON - MapServer.ROOT_ULLON) / depthSizeLon);

        if (result < 0) {
            return 0;
        }

        if (result > Math.pow(2, depth)) {
            return (int) Math.pow(2, depth);
        }

        return result;
    }

    private int xRight(Map<String, Double> params, int depth) {
        double paramsLRLON = Math.min(params.get("lrlon"), MapServer.ROOT_LRLON);

        double depthSizeLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        int result = (int) ((paramsLRLON - MapServer.ROOT_ULLON) / depthSizeLon);

        if (result < 0) {
            return 0;
        }

        if (result > Math.pow(2, depth)) {
            return (int) Math.pow(2, depth);
        }

        return result;
    }

    private int yUp(Map<String, Double> params, int depth) {
        double paramsULLAT = params.get("ullat");
        double depthSizeLat = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, depth);
        int result = (int) ((paramsULLAT - MapServer.ROOT_ULLAT) / depthSizeLat);

        if (result < 0) {
            return 0;
        }

        if (result > Math.pow(2, depth)) {
            return (int) Math.pow(2, depth);
        }

        return result;
    }

    private int yLower(Map<String, Double> params, int depth) {
        double paramsLRLAT = params.get("lrlat");
        double depthSizeLat = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, depth);
        int result = (int) ((paramsLRLAT - MapServer.ROOT_ULLAT) / depthSizeLat);

        if (result < 0) {
            return 0;
        }

        if (result > Math.pow(2, depth)) {
            return (int) Math.pow(2, depth);
        }

        return result;
    }

    private String[][] getGrid(int xLeft, int xRight, int yUp, int yLower, int depth) {
        int sizeY = Math.min(yLower - yUp + 1, (int) (Math.pow(2, depth)));
        int sizeX = Math.min(xRight - xLeft + 1, (int) (Math.pow(2, depth)));
        String[][] grids = new String[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                grids[i][j] = "d" + depth + "_x" + (xLeft + j) + "_y" + (yUp + i) + ".png";
                //System.out.println(grids[i][j]);
            }
        }
        return grids;
    }

    private double getRasterULLON(int xL, int depth) {
        double depthSizeLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        return MapServer.ROOT_ULLON + depthSizeLon * xL;
    }

    private double getRasterLRLON(int xR, int depth) {
        double depthSizeLon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        return MapServer.ROOT_ULLON + depthSizeLon * (xR + 1);
    }

    private double getRasterULLAT(int yU, int depth) {
        double depthSizeLat = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, depth);
        return MapServer.ROOT_ULLAT + depthSizeLat * yU;
    }

    private double getRasterLRLAT(int yL, int depth) {
        double depthSizeLat = (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, depth);
        return MapServer.ROOT_ULLAT + depthSizeLat * (yL + 1);
    }





}
