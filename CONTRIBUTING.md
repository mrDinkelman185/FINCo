# Contributing to FINCo

Thank you for your interest in contributing to FINCo!

## Development Setup

1. **Prerequisites**
   - Java 21 JDK
   - Node.js 20+
   - Docker & Docker Compose
   - Git

2. **Clone the Repository**
   ```bash
   git clone https://github.com/mrDinkelman185/FINCo.git
   cd FINCo
   ```

3. **Start Development Environment**
   ```bash
   docker-compose up -d
   ```

## Project Structure

```
FINCo/
├── backend/              # Java Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/     # Java source code
│   │   │   └── resources/ # Configuration & migrations
│   │   └── test/         # Unit tests
│   ├── pom.xml           # Maven configuration
│   └── Dockerfile
├── frontend/             # Next.js frontend
│   ├── src/
│   │   ├── app/          # Next.js app routes
│   │   ├── components/   # React components
│   │   ├── lib/          # Utility libraries
│   │   └── types/        # TypeScript types
│   ├── package.json
│   └── Dockerfile
├── terraform/            # Infrastructure as Code
│   ├── modules/          # Reusable Terraform modules
│   └── main.tf
└── .github/
    └── workflows/        # CI/CD pipelines
```

## Development Workflow

### Backend Development

```bash
cd backend

# Build
mvn clean install

# Run tests
mvn test

# Run locally
mvn spring-boot:run

# Lint
mvn checkstyle:check
```

### Frontend Development

```bash
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev

# Build
npm run build

# Lint
npm run lint

# Run tests
npm test
```

## Code Style

### Java
- Follow Google Java Style Guide
- Use Checkstyle configuration in `backend/checkstyle.xml`
- Maximum line length: 140 characters
- Use meaningful variable names
- Add JavaDoc for public methods

### TypeScript/JavaScript
- Follow ESLint configuration
- Use TypeScript strict mode
- Prefer functional components in React
- Use async/await over promises

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Integration Tests
```bash
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

## Submitting Changes

1. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make Changes**
   - Write clean, maintainable code
   - Add tests for new features
   - Update documentation

3. **Commit Changes**
   ```bash
   git add .
   git commit -m "feat: add new feature"
   ```

   Use conventional commits:
   - `feat:` New feature
   - `fix:` Bug fix
   - `docs:` Documentation
   - `style:` Code style changes
   - `refactor:` Code refactoring
   - `test:` Adding tests
   - `chore:` Maintenance tasks

4. **Push Changes**
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Create Pull Request**
   - Go to GitHub and create a PR
   - Fill in the PR template
   - Link any related issues
   - Wait for review

## Pull Request Guidelines

- Keep PRs focused and small
- Write clear PR descriptions
- Update tests and documentation
- Ensure CI passes
- Address review comments
- Squash commits before merge

## Security

- Never commit secrets or credentials
- Use environment variables for configuration
- Report security vulnerabilities privately
- Follow security best practices

## Code Review Process

1. Automated checks must pass
2. At least one approval required
3. No unresolved comments
4. Up to date with main branch

## Questions?

- Open an issue on GitHub
- Check existing documentation
- Review closed issues and PRs

## License

By contributing, you agree that your contributions will be licensed under the project's license.
