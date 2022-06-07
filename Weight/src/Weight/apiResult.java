package Weight;

class Result {
	int totalHits;
	SearchResultFood[] foods;
}

class SearchResultFood {
	int fdcId;
	String description;
	AbridgedFoodNutrient[] foodNutrients;
	measure[] foodMeasures;
}

class AbridgedFoodNutrient {
	String nutrientName;
	double value;
	String unitName;
}

class measure{
	String disseminationText;
	double gramWeight;
}
