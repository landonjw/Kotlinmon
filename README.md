# Kotlinmon
Name TBD

## Development Practices
* Do not import from client package in server package, or vice versa. Common implementations between client and server reside in the common package.
* Use dependency injection for services, rather than direct initialization
* Prefer composition over inheritance, where applicable.
    * Particular emphasis should be given for entities or problem spaces that would resolve into a convoluted hierarchy or god object.
