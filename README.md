# Kotlinmon
Name TBD

## Development Practices
* Do not reach from client package to server package, or vice versa
* Use dependency injection for services, rather than direct implementation construction
* Prefer composition over inheritance, where applicable.
    * Particular emphasis should be given for entities or problem spaces that would resolve into a convoluted hierarchy or god object.
