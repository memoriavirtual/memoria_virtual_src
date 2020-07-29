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
  customContainerStyle,
  customLabelStyle,
}) => (
    <TouchableOpacity onPress={action}>
      <View
        style={customContainerStyle ? customContainerStyle : [
          styles.buttonContainer,
          styles.enabledButtonBackground,
        ]}
      >
        <Text style={customLabelStyle ? customLabelStyle : styles.buttonLabel}>
          {buttonLabel}
        </Text>
        {
          icon ? <View style={styles.iconContainer}>
            {icon}
          </View>
          : null
        }
      </View>
    </TouchableOpacity>
);

export default CustomButton;
