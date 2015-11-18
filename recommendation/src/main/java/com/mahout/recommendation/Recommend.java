package com.mahout.recommendation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class Recommend
{
    public static void main( String[] args ) throws IOException, TasteException
    {
    	
    	DataModel model = new FileDataModel(new File("data/ydata-ymovies-user-movie-ratings-train-v1_0.txt"));
    	// User-based recommender with Pearson Correlation Similarity
    	RecommenderBuilder userBuilder = new RecommenderBuilder(){
    		public Recommender buildRecommender(DataModel model) throws TasteException{
    			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
    			UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
    			return new GenericUserBasedRecommender(model,neighborhood,similarity);
    		}
    	};
    	
    	Recommender userRecommender = userBuilder.buildRecommender(model);
    	int userID = 4;
    	int itemNum = 3;
    	List<RecommendedItem> recommendationItem = userRecommender.recommend(userID, itemNum);
    	System.out.println("User-based Recommendation Result:");
  	  	System.out.println("For User: "+ userID + ", Recommend " + itemNum + " Items ");
    	for (RecommendedItem recommendation : recommendationItem) {
    	  System.out.println(recommendation);
    	}
    	System.out.println();
    	//evaluate the User-based recommender
    	AverageAbsoluteDifferenceRecommenderEvaluator evaluatorUser = new AverageAbsoluteDifferenceRecommenderEvaluator();
    	System.out.println("Evaluate the User-based Recommender");
    	System.out.println("Use Average Absolute Difference");
    	double outUser = 0;
    	for (int i = 1;i<=10;i++){
    		double scoreUser = evaluatorUser.evaluate(userBuilder, null, model, 0.95, 0.05);
    		outUser += scoreUser;
    		System.out.println(i+"th trail score: "+ scoreUser);
    	}
    	System.out.println("Average score of 10 trails: "+ outUser/10);
    	System.out.println();
    	
    	//Item-based recommender with Pearson Correlation Similarity
    	RecommenderBuilder itemBuilder = new RecommenderBuilder(){
    		public Recommender buildRecommender(DataModel model) throws TasteException{
    			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
    			return new GenericItemBasedRecommender(model,similarity);
    		}
    	};
    	
    	Recommender itemRecommender = itemBuilder.buildRecommender(model);
    	List<RecommendedItem> recommendationsUser = itemRecommender.recommend(4, 3);
    	System.out.println("Item-based Recommendation Result:");
    	System.out.println("For User: "+ userID + ", Recommend " +itemNum+ " Items ");
    	for (RecommendedItem recommendation : recommendationsUser) {
    	  System.out.println(recommendation);
    	}
    	System.out.println();
    	
    	//evaluate the Item-based recommender
    	AverageAbsoluteDifferenceRecommenderEvaluator evaluatorItem = new AverageAbsoluteDifferenceRecommenderEvaluator();
    	System.out.println("Evaluate the Item-based Recommender");
    	System.out.println("Use Average Absolute Difference");
    	double outItem = 0;
    	for (int i = 1;i<=10;i++){
    		double scoreItem = evaluatorItem.evaluate(itemBuilder, null, model, 0.95, 0.05);
    		outItem += scoreItem;
    		System.out.println(i+"th trail score: "+ scoreItem);
    	}
    	System.out.println("Average score of 10 trails: "+ outItem/10);
    	System.out.println();
    	
    	//SVD recommender
    	RecommenderBuilder SVDBuilder = new RecommenderBuilder(){
    		public Recommender buildRecommender(DataModel model) throws TasteException{
    		    int featureNum = 10;
    		    double factor = 0.05;
    		    int trainNum = 10;
    			return new SVDRecommender(model,new ALSWRFactorizer(model,featureNum,factor,trainNum));
    		}
    	};
    	Recommender SVDRecommender = SVDBuilder.buildRecommender(model);
    	List<RecommendedItem> recommendationsSVD = SVDRecommender.recommend(4, 3);
    	System.out.println("SVD Recommendation Result:");
    	System.out.println("For User: "+ userID + ", Recommend " + itemNum + " Items ");
    	for (RecommendedItem recommendation : recommendationsSVD) {
    	  System.out.println(recommendation);
    	}
    	System.out.println();

 	//evaluate the SVD recommender
	AverageAbsoluteDifferenceRecommenderEvaluator evaluatorSVD = new AverageAbsoluteDifferenceRecommenderEvaluator();
	System.out.println("Evaluate the SVD Recommender");
	System.out.println("Use Average Absolute Difference");
	double outSVD = 0;
	for (int i = 1;i<=10;i++){
		double scoreSVD = evaluatorSVD.evaluate(SVDBuilder, null, model, 0.9, 0.1);
		outSVD += scoreSVD;
		System.out.println(i+"th trail score: "+ scoreSVD);
	}
	System.out.println("Average score of 10 trails: "+ outSVD/10);
   }
}




