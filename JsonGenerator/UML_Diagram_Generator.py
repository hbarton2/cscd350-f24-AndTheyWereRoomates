import os
import re
import json


def extract_class_name(content):
    """Extract the class name from Java code."""
    match = re.search(r'\bpublic\s+class\s+(\w+)', content)
    return match.group(1) if match else None


def extract_fields(content):
    """
    Extract fields from Java code.
    Handles modifiers, generics, and arrays.
    """
    fields = []
    # Match field declarations, including static/final and generic types
    matches = re.findall(
        r'\b(private|protected|public)\s+(static\s+)?(final\s+)?([\w<>[\]]+)\s+(\w+);',
        content)
    for match in matches:
        field_type = match[3]  # Type (e.g., List<String>, int[], etc.)
        field_name = match[4]  # Field name
        fields.append({"type": field_type, "name": field_name})
    return fields


def extract_methods(content):
    """
    Extract methods from Java code.
    Handles return types, parameters, and overloading.
    """
    methods = []
    matches = re.findall(
        r'\b(private|protected|public)\s+(static\s+)?([\w<>[\]]+)\s+(\w+)\((.*?)\)',
        content)
    for match in matches:
        return_type = match[2]
        method_name = match[3]
        params = match[4]
        parameters = []
        if params.strip():
            param_list = params.split(",")
            for param in param_list:
                param_parts = param.strip().split()
                if len(param_parts) == 2:  # Type and name
                    parameters.append(
                        {"type": param_parts[0], "name": param_parts[1]})
        methods.append({
            "type": return_type,
            "name": method_name,
            "parameters": parameters,
            "isOverloaded": False  # Placeholder for overload detection
        })
    return methods


def extract_relationships(content, all_class_names):
    """
    Extract relationships (e.g., extends, implements, aggregation, composition).
    Validates target classes against the list of all class names.
    """
    relationships = []

    # Generalization: "extends"
    generalization_match = re.search(r'\bclass\s+\w+\s+extends\s+(\w+)',
                                     content)
    if generalization_match:
        target = generalization_match.group(1)
        if target in all_class_names:
            relationships.append({"type": "Generalization", "target": target})

    # Realization: "implements"
    realization_match = re.search(r'\bclass\s+\w+\s+implements\s+([\w, ]+)',
                                  content)
    if realization_match:
        interfaces = realization_match.group(1).split(",")
        for interface in interfaces:
            interface = interface.strip()
            if interface in all_class_names:
                relationships.append(
                    {"type": "Realization", "target": interface})

    # Aggregation and Composition
    field_matches = re.findall(
        r'\b(private|protected|public)\s+([\w<>[\]]+)\s+(\w+);', content)
    for field_match in field_matches:
        field_type = field_match[1]
        if field_type in all_class_names:
            relationships.append({"type": "Aggregation", "target": field_type})
        # Check for Composition (fields instantiated with 'new')
        if re.search(r'\bnew\s+' + re.escape(field_type) + r'\(', content):
            relationships.append({"type": "Composition", "target": field_type})

    return relationships


def parse_java_file(file_path, all_class_names):
    """Parse a single Java file to extract class details."""
    with open(file_path, 'r') as file:
        content = file.read()

    class_name = extract_class_name(content)
    if not class_name:
        return None  # Skip files without a valid class

    fields = extract_fields(content)
    methods = extract_methods(content)
    relationships = extract_relationships(content, all_class_names)

    return {
        "className": class_name,
        "fields": fields,
        "methods": methods,
        "relationships": relationships,
        "position": [0, 0]  # Default position for visualization
    }


def process_directory(directory_path):
    """
    Process all Java files in the directory to extract UML class data.
    Ensures relationships are valid.
    """
    # First pass: Collect all class names
    all_class_names = set()
    for root, _, files in os.walk(directory_path):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                with open(file_path, 'r') as f:
                    content = f.read()
                class_name = extract_class_name(content)
                if class_name:
                    all_class_names.add(class_name)

    # Second pass: Parse each file with validated relationships
    uml_data = []
    for root, _, files in os.walk(directory_path):
        for file in files:
            if file.endswith('.java'):
                file_path = os.path.join(root, file)
                class_data = parse_java_file(file_path, all_class_names)
                if class_data:
                    uml_data.append(class_data)

    return uml_data


def save_json(data, output_path):
    """Save the extracted UML data to a JSON file."""
    with open(output_path, 'w') as json_file:
        json.dump(data, json_file, indent=4)


if __name__ == "__main__":
    # Input directory containing Java files
    input_directory = "./"  # Replace with your Java files directory
    # Output JSON file path
    output_file = "uml_project.json"

    # Process directory and save to JSON
    uml_data = process_directory(input_directory)
    save_json(uml_data, output_file)

    print(f"UML data saved to {output_file}")
