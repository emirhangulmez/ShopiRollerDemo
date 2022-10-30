package com.emirhan.shopirollerdemo.core

class Constants {
    companion object {
        //..

        // Retrofit
        const val BASE_URL = "https://api.shopiroller.com/"

        // ShopiRoller Configuration
        // Keys
        const val API_KEY = "Api-Key: xXspnfUxPzOGKNu90bFAjlOTnMLpN8veiixvEFXUw9I="
        const val ALIAS_KEY = "Alias-Key: AtS1aPFxlIdVLth6ee2SEETlRxk="

        // End Points
        const val CATEGORIES_ENDPOINT = "/v2.0/categories"
        const val PRODUCTS_ENDPOINT = "/v2.0/products/advanced-filtered"
        const val PRODUCT_ENDPOINT = "/v2.0/products/{productID}"

        // Featured Category Id
        const val FEATURED_CATEGORY_ID = "61b1f161a82ec0dd1c56f5ee"

        // Room DB Configuration
        const val PRODUCT_TABLE = "product_table"

        // Navigation - Arguments
        const val CATEGORY_ID = "categoryID"
        const val PRODUCT_ID = "productID"
        const val IMAGE = "image"
        const val PRODUCTS = "products"

        // Texts in Content
        // Home Screen
        const val SELECT_CATEGORY = "Please select a category"
        const val SEARCH_RESULTS = "Search Results"
        const val PRODUCTS_HEADER = "Products"

        //Products Screen
        const val FILTER = "Filter"

        // Sorting in Products Screen
        const val PRICE = "Price"
        const val TITLE = "Title"
        const val DATE = "Date"

        // Stock Badge
        const val IN_STOCK = "In stock"
        const val NO_STOCK = "No Stock!"

        // Product Screen

        // Content Descriptions for Accessibility
        // Home Screen
        const val CATEGORY_IMAGE = "Category Image"
        const val SEARCH_ICON = "Search Icon"
        const val BASKET_ICON = "Basket icon"
        const val SETTINGS_ICON = "Settings Icon"
        const val APP_LOGO = "Application Logo"

        // Products Screen
        const val NAVIGATE_BACK = "Navigate Back Button"
        const val FILTER_BUTTON = "Filter Button"
        const val PRODUCT_IMAGE = "Product Image"

        // Product Screen
        const val NAVIGATE_PRODUCTS = "Navigate To Products Button"
        const val ADD_TO_BASKET = "Add To Basket"
        const val REACHED_PRODUCTS = "You have reached the number of products you can buy!"
        const val ADDED_TO_BASKET = "Added to basket!"

        // Button Texts
        const val GO_TO_PAYMENT = "Go to payment"

        // Custom Actions
        const val TOTAL_ACTION = "total"
        const val SEARCH_ACTION = "Search"

        // ShopiRoller URLs
        const val MAIN_URL = "https://www.shopiroller.com/"
        const val PAYMENT_URL =
            "https://support.shopiroller.com/en/knowledgebase/which-payment-methods-can-i-use-with-shopiroller/"

        // Currency
        const val USD = "USD"

        // Default Texts
        const val PRICE_DEFAULT = "0.0 $"
    }
}