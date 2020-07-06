import React from 'react';
import {
  View,
  StyleSheet,
  TouchableOpacity,
  Text,
} from 'react-native';

import buttonStyles from '../styles/customButton';

const styles = StyleSheet.create({
  ...buttonStyles(),
});

const CustomButton = ({
  action,
  buttonLabel,
  icon,
}) => (
    <TouchableOpacity onPress={action}>
      <View
        style={[
          styles.buttonContainer,
          styles.enabledButtonBackground
        ]}
      >
        <Text style={styles.buttonLabel}>{buttonLabel}</Text>
        <View style={styles.iconContainer}>
          {icon}
        </View>
      </View>
    </TouchableOpacity>
);

export default CustomButton;