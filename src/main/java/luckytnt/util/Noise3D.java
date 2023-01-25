package luckytnt.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.phys.Vec3;

public class Noise3D {
	
	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;
	public final int scale;
	public NoisePoint[][][] noisePoints;
	public List<NoisePoint> startingPoints = new ArrayList<>();
	
	public Noise3D(int sizeX, int sizeY, int sizeZ, int scale) {
		this.sizeX = sizeX - 1;
		this.sizeY = sizeY - 1;
		this.sizeZ = sizeZ - 1;
		this.scale = scale;
		noisePoints = new NoisePoint[sizeX][sizeY][sizeZ];
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				for(int z = 0; z < sizeZ; z++) {
					noisePoints[x][y][z] = new NoisePoint(new Vec3(x, y, z));
				}
			}
		}
		calculateStartingPoints();
		calculateNoise();
	}
	
	public void calculateStartingPoints() {
		//Starting Points calculation
		for(int x = 0; x < sizeX; x += scale) {
			for(int y = 0; y < sizeY; y += scale) {
				for(int z = 0; z < sizeZ; z += scale) {
					if(!(x > sizeX) && !(y > sizeY)) {
						if((x == 0 || y == 0 || z == 0) || (x == sizeX || y == sizeY || z == sizeZ)) {
							noisePoints[x][y][z].setValue(0);
						}
						else {
							noisePoints[x][y][z].setValue(Math.random());
						}
					}
				}
			}
		}
	}
	
	public void calculateNoise() {		
		//X-Line calculation
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y += scale) {
				for(int z = 0; z < sizeZ; z += scale) {
					if(!noisePoints[x][y][z].isSet()) {
						int previousX = x - (x % scale);
						int nextX = (previousX + scale) >= sizeX ? sizeX - 1 : (previousX + scale);
						noisePoints[x][y][z].setValue(noisePoints[previousX][y][z].getValue() - /*lerp calculation ->*/(noisePoints[previousX][y][z].getValue() / scale - noisePoints[nextX][y][z].getValue() / scale) * (x % scale) /*<- step calculation*/);
					}
				}
			}
		}
		//Plane calculation
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				for(int z = 0; z < sizeZ; z += scale) {
					if(!noisePoints[x][y][z].isSet()) {
						int previousY = y - (y % scale);
						int nextY = (previousY + scale) >= sizeY ? sizeY - 1 : (previousY + scale);
						noisePoints[x][y][z].setValue(noisePoints[x][previousY][z].getValue() - /*lerp calculation ->*/(noisePoints[x][previousY][z].getValue() / scale - noisePoints[x][nextY][z].getValue() / scale) * (y % scale) /*<- step calculation*/);
					}
				}
			}
		}
		//3D calculation
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				for(int z = 0; z < sizeZ; z++) {
					if(!noisePoints[x][y][z].isSet()) {
						int previousZ = z - (z % scale);
						int nextZ = (previousZ + scale) >= sizeZ ? sizeZ - 1 : (previousZ + scale);
						noisePoints[x][y][z].setValue(noisePoints[x][y][previousZ].getValue() - /*lerp calculation ->*/(noisePoints[x][y][previousZ].getValue() / scale - noisePoints[x][y][nextZ].getValue() / scale) * (z % scale) /*<- step calculation*/);
					}
				}
			}
		}
	}
	
	public double getValue(int x, int y, int z) {
		if(x > sizeX || y > sizeY || z > sizeZ || x == 0 || y == 0 || z == 0) {
			return 0;
		}
		else {
			return noisePoints[x][y][z].getValue();
		}
	}
	
	public static class NoisePoint{
		
		private Vec3 position;
		private double value;
		private boolean set;
		
		public NoisePoint(Vec3 position) {
			this.position = position;
		}
		
		public void setValue(double value) {
			this.value = value;
			set = true;
		}
		
		public Vec3 getPosition() {
			return position;
		}
		
		public double getValue() {
			return value;
		}
		
		public boolean isSet() {
			return set;
		}
	}
}
