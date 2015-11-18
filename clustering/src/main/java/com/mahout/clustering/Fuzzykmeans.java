package com.mahout.clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.math.NamedVector;

public class Fuzzykmeans 
{
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException{

		Configuration conf = new Configuration();
		Path path = new Path("/Users/raymond/Desktop/HW2/workdir/clustering" + "/reuters-fuzzykmeans/clusteredPoints/part-m-00000");
		SequenceFile.Reader reader = new SequenceFile.Reader(conf,Reader.file(path));
		IntWritable key = new IntWritable();
		WeightedPropertyVectorWritable value = new WeightedPropertyVectorWritable();
		
		MultiMap multiMap = new MultiValueMap();
		while (reader.next(key, value)) {
			NamedVector nVec = (NamedVector) value.getVector();
			multiMap.put(key.toString(), nVec.getName());
			System.out.println("Filename:" + nVec.getName() + ", ClusterId=" + key.toString());
		}
		System.out.println("Retrieve Related documents");
		Set<String> keys = multiMap.keySet();
		for (String keyval : keys) {
            System.out.println("ClusterID = " + keyval);
            ArrayList files = (ArrayList) multiMap.get(keyval);
            for (int i = 0; i < files.size(); i++) {
        	    String id = (String) files.get(i);
        	    System.out.println("Filename: " + id);
        	}
        }
		reader.close();
		}
}
