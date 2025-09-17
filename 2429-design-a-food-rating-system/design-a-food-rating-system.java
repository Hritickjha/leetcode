import java.util.*;

public class FoodRatings {
    private static class Entry {
        String food;
        int rating;
        Entry(String food, int rating) {
            this.food = food;
            this.rating = rating;
        }
    }

    // food -> cuisine
    private Map<String, String> foodToCuisine;
    // food -> current rating
    private Map<String, Integer> foodToRating;
    // cuisine -> max-heap of Entries (rating snapshot + food)
    private Map<String, PriorityQueue<Entry>> cuisineToPQ;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        foodToCuisine = new HashMap<>();
        foodToRating = new HashMap<>();
        cuisineToPQ = new HashMap<>();

        Comparator<Entry> comp = (a, b) -> {
            if (a.rating != b.rating) return Integer.compare(b.rating, a.rating); // higher rating first
            return a.food.compareTo(b.food); // lexicographically smaller first
        };

        for (int i = 0; i < foods.length; i++) {
            String f = foods[i];
            String c = cuisines[i];
            int r = ratings[i];

            foodToCuisine.put(f, c);
            foodToRating.put(f, r);

            cuisineToPQ.computeIfAbsent(c, k -> new PriorityQueue<>(comp)).add(new Entry(f, r));
        }
    }

    public void changeRating(String food, int newRating) {
        String cuisine = foodToCuisine.get(food);
        // update current rating
        foodToRating.put(food, newRating);
        // push a new snapshot entry into the cuisine heap (lazy deletion)
        cuisineToPQ.get(cuisine).add(new Entry(food, newRating));
    }

    public String highestRated(String cuisine) {
        PriorityQueue<Entry> pq = cuisineToPQ.get(cuisine);
        // Pop outdated entries until the top matches current rating
        while (true) {
            Entry top = pq.peek();
            // top should never be null by problem constraints (cuisine exists)
            if (top == null) return "";
            int current = foodToRating.get(top.food);
            if (top.rating == current) return top.food;
            pq.poll(); // outdated snapshot, discard
        }
    }
}

